from django.db import models
from django.contrib.auth.models import User
import sysadmin.models
import teacher.models
from django.db.models.signals import pre_save, pre_delete
from django.dispatch import receiver

class Student(models.Model):
    '''
    [学生表]
    sid:学号
    sname:姓名
    user:Django自带user
    schoolid:学院号
    '''
    sid=models.CharField(max_length=100,blank=False,unique=True,primary_key=True)  
    sname=models.CharField(max_length=100,blank=False)  
    user = models.OneToOneField(
        to=User,
        on_delete=models.CASCADE,
        blank=False
    )
    schoolid=models.ForeignKey(
        to=sysadmin.models.School,
        on_delete=models.CASCADE,
        related_name='student',
        to_field="schoolid",
        blank=False
    )

class ChooseCourse(models.Model):
    '''
    [选课表]
    sid:学号
    ocid:开课号
    pscore:平时成绩
    kscore:考试成绩
    zscore:总评成绩
    '''
    sid=models.ForeignKey(
        to=Student,
        on_delete=models.CASCADE,
        related_name='choose_course',
        to_field="sid",
        blank=False, 
    )
    ocid = models.ForeignKey(
        to=teacher.models.OfferCourse,
        on_delete=models.CASCADE,
        related_name='choose_course',
        to_field="id",
        blank=False,
    )
    pscore=models.FloatField(default=-1)
    kscore=models.FloatField(default=-1)
    zscore=models.FloatField(default=-1)

@receiver(pre_save, sender=ChooseCourse)
def pre_save_choosecourse(sender, instance, **kwargs):
    offercourse = instance.ocid
    offercourse.choosenum += 1
    offercourse.save()

@receiver(pre_delete, sender=ChooseCourse)
def pre_delete_choosecourse(sender, instance, **kwargs):
    offercourse = instance.ocid
    offercourse.choosenum -= 1
    offercourse.save()
    
