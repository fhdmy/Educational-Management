from django.contrib.auth.models import User
from django.contrib.auth import authenticate
from rest_framework import generics, permissions, views, viewsets
from rest_framework.response import Response
from rest_framework.authtoken.models import Token
from rest_framework.authtoken.views import ObtainAuthToken
from rest_framework.decorators import action, permission_classes
from django.db.models.functions import Left,Substr,StrIndex,Cast
from xks.authentication import has_expired
from django.db.models import Value as V,F,Avg,Max,Min,Count,Sum,ExpressionWrapper,CharField,IntegerField,Exists,BooleanField

import student.models
import teacher.models
import sysadmin.models
import student.serializers
import base64
import json

def is_number(text):
     try:
         z=int(text)
         return isinstance(z,int)
     except ValueError:
         return False

class ChooseCourseViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = student.models.ChooseCourse.objects.all()
    serializer_class = student.serializers.ChooseCourseSerializer
    filter_fields = ['ocid']
    ordering_fields = '__all__'

class StudentViewSet(viewsets.ModelViewSet):
    queryset = student.models.Student.objects.all()
    serializer_class=student.serializers.StudentSerializer

    @action(methods=['post'], detail=False, permission_classes=[permissions.AllowAny])
    def login(self, request, *args, **kwargs):
        info={}
        if request.data['sid']=="" or request.data['password']=="":
            return Response('Serializer is invalid', 400)
        try:
            sname = student.models.Student.objects.get(
                sid=request.data['sid'],
        ).sname
        except student.models.Student.DoesNotExist:
            return Response('Serializer is invalid', 400)
        info["username"]=sname
        info["password"]=request.data['password']
        serializer = student.serializers.LoginSerializer(
            data=info,
            context={'request': request}
        )
        if serializer.is_valid():
            user = serializer.validated_data['user']
            if has_expired(user.auth_token):
                user.auth_token.delete()
            token, created = Token.objects.get_or_create(user=user)
            return Response({
                'token': token.key
            })
        else:
            return Response('Serializer is invalid', 400)
    
    @action(methods=['post'], detail=False, permission_classes=[permissions.IsAuthenticated])
    def choose_course(self, request, *args, **kwargs):
        serializer=student.serializers.ChooseCourseSerializer(data=request.data)
        user = request.user
        if serializer.is_valid():
            try:
                stu=user.student
                oc=teacher.models.OfferCourse.objects.get(id=request.data["ocid"])
                choose_course,created=student.models.ChooseCourse.objects.get_or_create(
                    sid=stu,
                    ocid=oc,
                )
            except:
                return Response('Serializer is invalid', 400)
            return Response('Choose Course Success', 200)
        else:
            return Response('Serializer is invalid', 400)
    
    @action(methods=['post'], detail=False, permission_classes=[permissions.IsAuthenticated])
    def cancel_choose_course(self, request, *args, **kwargs):
        serializer=student.serializers.ChooseCourseSerializer(data=request.data)
        user = request.user
        if serializer.is_valid():
            try:
                stu=user.student
                oc=teacher.models.OfferCourse.objects.get(id=request.data["ocid"])
                choose_course=student.models.ChooseCourse.objects.get(
                    sid=stu,
                    ocid=oc,
                )
                choose_course.delete()
            except:
                return Response('Serializer is invalid', 400)
            return Response('Cancel Course Success', 200)
        else:
            return Response('Serializer is invalid', 400)
    
class GetOfferCourses(views.APIView):
    '''
    多条件模糊查询可选课
    '''
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request, *args, **kwargs):
        params = request.query_params
        user=request.user
        q={}
        cid_list=list()
        cname_list=list()
        score_list=list()
        termid=4
        q['termid']=termid
        if params['cid']!="":
            cid_list= list(map(lambda x: x['cid'], [{'cid':params['cid']}]))
        if params['cname']!="":
            cids=sysadmin.models.Course.objects.filter(cname__icontains=params['cname']).order_by("cid").values("cid").distinct()
            cname_list = list(map(lambda x: x['cid'], cids))
        if params['score']!="":
            try:
                score_cids=sysadmin.models.Course.objects.filter(score=int(params['score'])).order_by("cid").values("cid").distinct()
            except:
                score_cids=[]
            score_list = list(map(lambda x: x['cid'], score_cids))

        if params['cid']!="" or params['cname']!="" or params['score']!="":
            flag=True
            if params['cid']!="":
                con_clist=cid_list
                flag=False
            else:
                con_clist=list()
            if (not flag) and params['cname']!="": #交
                con_clist=list(set(con_clist).intersection(set(cname_list)))
            elif flag and params['cname']!="": #并
                con_clist=cname_list
                flag=False
            if (not flag) and params['score']!="": #交
                con_clist=list(set(con_clist).intersection(set(score_list)))
            elif flag and params['score']!="": #并
                con_clist=score_list
                flag=False
            if not flag:
                q['cid__in']=con_clist

        tid_list=list()
        tname_list=list()
        if params['tid']!="":
            tid_list= list(map(lambda x: x['tid'], [{'tid':params['tid']}]))
        if params['tname']!="":
            tids=teacher.models.Teacher.objects.filter(tname__icontains=params['tname']).order_by("tid").values("tid").distinct()
            tname_list = list(map(lambda x: x['tid'], tids))

        if params['tid']!="" or params['tname']!="":
            flag=True
            if params['tid']!="":
                con_tlist=tid_list
                flag=False
            else:
                con_tlist=list()
            if (not flag) and params['tname']!="": #交
                con_tlist=list(set(con_tlist).intersection(set(tname_list)))
            elif flag and params['tname']!="": #并
                con_tlist=tname_list
                flag=False
            q['tid__in']=con_tlist

        if params['xname']!="":
            try:
                xid=sysadmin.models.Campus.objects.get(xname__icontains=params['xname'])
            except:
                xid=-1
            q['xid']=xid
        q["status"]=0

        qstart=0
        qend=0
        time_flag=False
        if params['time']!="":
            try:
                qstart=int(params['time'].split("-")[0][1:])
                qend=int(params['time'].split("-")[1])
                time_flag=True
            except:
                q['time__icontains']=params['time']
        if time_flag:
            q['course_day']=params['time'][0]
            q['cstart__gte']=qstart
            q['cend__lte']=qend
            offer_courses=teacher.models.OfferCourse.objects.annotate(
                course_day=Substr(F('time'),1,1),
                cstart=Cast(Substr(F('time'),2,StrIndex(F('time'),V('-'))-2,output_field=CharField()),IntegerField()),
                cend=Cast(Substr(F('time'),StrIndex(F('time'),V('-'))+1,output_field=CharField()),IntegerField())
            ).filter(
                **q
            ).order_by("cid")
        else:
            offer_courses=teacher.models.OfferCourse.objects.annotate(
                cc_sid=Cast(F('choose_course__sid'),IntegerField())
            ).filter(
                **q
            ).order_by("cid")
        rtn=[]
        for instance in offer_courses:
            serializer = student.serializers.GetOfferCoursesSerializer(
                instance=instance,
                context={'request': request}
            )
            oc=serializer.data
            chosen=(instance.id==int(user.student.sid))
            #chosen
            # chosen=student.models.ChooseCourse.objects.filter(
            #     sid = user.student,
            #     ocid = instance.id
            # ).exists()
            oc["chosen"]=chosen
            rtn.append(oc)
        return Response(rtn)

class GetChosenCourses(views.APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request, *args, **kwargs):
        params = request.query_params
        user = request.user
        stu=user.student
        chosen_courses=student.models.ChooseCourse.objects.filter(
            sid=stu,ocid__status=0
        ).order_by("ocid")
        rtn=[]
        for instance in chosen_courses:
            serializer = student.serializers.ChosenCoursesSerializer(
                instance=instance,
                context={'request': request}
            )
            rtn.append(serializer.data)
        return Response(rtn)

class GetCourseScore(views.APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request, *args, **kwargs):
        params = request.query_params
        user=request.user
        stu=user.student
        chosen_courses=student.models.ChooseCourse.objects.filter(
            sid=stu,ocid__status=2
        ).order_by("ocid")
        rtn=[]
        for instance in chosen_courses:
            serializer = student.serializers.CourseScoreSerializer(
                instance=instance,
                context={'request': request}
            )
            rtn.append(serializer.data)
        return Response(rtn)