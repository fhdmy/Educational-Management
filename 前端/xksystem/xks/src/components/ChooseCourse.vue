<template>
  <div>
    <v-snackbar v-model="unlogin" multi-line="multi-line" :top="y === 'top'" :timeout="timeout">
      请先登录！ 
      <v-btn color="red" text @click="unlogin = false">关闭</v-btn>
    </v-snackbar>
    <div class="input-wrapper">
      <v-row>
        <v-col cols="12" md="3">
          <v-text-field v-model="course_id" label="课程号" outlined dense></v-text-field>
        </v-col>

        <v-col cols="12" md="3">
          <v-text-field v-model="course_name" label="课程名称" outlined dense></v-text-field>
        </v-col>

        <v-col cols="12" md="3">
          <v-text-field v-model="score" label="学分数" outlined dense></v-text-field>
        </v-col>

        <v-col cols="12" md="3">
          <v-text-field v-model="xname" label="校区" outlined dense></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12" md="3">
          <v-text-field v-model="teacher_id" label="教师号" outlined dense></v-text-field>
        </v-col>
        <v-col cols="12" md="3">
          <v-text-field v-model="teacher_name" label="教师姓名" outlined dense></v-text-field>
        </v-col>
        <v-col cols="12" md="3">
          <v-text-field v-model="course_time" label="上课时间" outlined dense></v-text-field>
        </v-col>
      </v-row>
    </div>
    <v-data-table :headers="headers" :items="courses" :items-per-page="10">
      <template v-slot:item.action="{ item }">
        <v-btn @click="choose_item(item)" color="error" v-show="!item.chosen">选课</v-btn>
        <v-btn @click="delete_item(item)" color="primary" v-show="item.chosen">退课</v-btn>
      </template>
    </v-data-table>
  </div>
</template>

<script>
export default {
  name: "ChooseCourse",
  components: {},
  created() {
    if(this.token!="")
      this.update_data();
  },
  data: () => ({
    unlogin:false,
    y: "top",
    timeout: 2000,

    course_id: "",
    course_name: "",
    score: "",
    teacher_id: "",
    teacher_name: "",
    course_time:"",
    xname:"",

    headers: [
      {
        text: "人数",
        align: "start",
        value: "pnum"
      },
      {
        text: "课程",
        value: "course"
      },
      {
        text: "教师",
        value: "teacher"
      },
      {
        text: "上课时间",
        value: "time"
      },
      {
        text: "地点",
        value: "place"
      },
      {
        text: "学分",
        value: "score"
      },
      {
        text: "行为",
        value: "action",
        sortable: false
      }
    ],
    courses:[],
  }),
  props:{
    token:{
      type:String,
      default:""
    },
    sid:{
      type:String,
      default:""
    }
  },
  watch: {
    course_id() {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        this.handleChange();
      }, 1000);
    },
    course_name() {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        this.handleChange();
      }, 1000);
    },
    score() {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        this.handleChange();
      }, 1000);
    },
    teacher_id() {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        this.handleChange();
      }, 1000);
    },
    teacher_name() {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        this.handleChange();
      }, 1000);
    },
    xname() {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        this.handleChange();
      }, 1000);
    },
    course_time() {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        this.handleChange();
      }, 1000);
    },
    sid() {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        this.handleChange();
      }, 1000);
    }
  },
  methods: {
    choose_item(item) {
      const index = this.courses.indexOf(item);
      if(this.sid==""){
        this.unlogin=true;
        return;
      }
      this.$http({
        method: "POST",
        url:"/api/student/students/choose_course/",
        data:{
          ocid:parseInt(item.ocid),
        },
        headers: {
          "Authorization": this.token
        },
      })
        .then(res => {
          if(res.status==200){
            var psplit=item.pnum.split("/");
            this.$set(this.courses[index],"pnum",parseInt(Number(psplit[0])+1)+"/"+psplit[1]);
            this.$set(this.courses[index],"chosen",true);
          }
        })
        .catch(error => {
          console.log(error.response);
        });
    },
    delete_item(item) {
      const index = this.courses.indexOf(item);
      if (confirm("确定要退课?")) {
        this.$http({
          method: "POST",
          url: "/api/student/students/cancel_choose_course/",
          data:{
              ocid:parseInt(item.ocid),
          },
          headers: {
            "Authorization": this.token
          },
        })
          .then(res => {
            if (res.status == 200) {
              var psplit=item.pnum.split("/");
              this.$set(this.courses[index],"pnum",parseInt(Number(psplit[0])-1)+"/"+psplit[1]);
              this.$set(this.courses[index],"chosen",false);
            }
          })
          .catch(error => {
            console.log(error.response);
          });
      }
    },
    handleChange() {
      //执行发送请求
      this.update_data();
    },
    update_data() {
      this.$http({
        method: "get",
        url:"/api/student/get_offer_courses/?cid=" +
          this.course_id +
          "&cname=" +
          this.course_name +
          "&score=" +
          this.score +
          "&tid=" +
          this.teacher_id +
          "&tname=" +
          this.teacher_name+
          "&xname=" +
          this.xname+
          "&time=" +
          this.course_time,
        headers: {
          "Authorization": this.token
        },
      })
        .then(res => {
          this.courses=[];
          for(var k=0;k<res.data.length;k++)
          {
            var pn=res.data[k].choosenum+"/"+res.data[k].maxnum;
            var c=res.data[k].cid.cname+"  "+res.data[k].cid.cid;
            var t=res.data[k].tid.tname+"  "+res.data[k].tid.tid;
            var xname=res.data[k].xid.xname;
            this.$set(this.courses, k, {
              pnum: pn,
              course: c,
              teacher: t,
              time: res.data[k].time,
              place:"["+xname + "] " +res.data[k].place,
              score: res.data[k].cid.score,
              chosen:res.data[k].chosen,
              ocid:res.data[k].id,
            });
          }
        })
        .catch(error => {
          console.log(error.response);
        });
    }
  }
};
</script>

<style scoped>
.col-12 {
  padding-top: 0;
  padding-bottom: 0;
}
.input-wrapper {
  border-bottom: 1px solid #bbbbbb;
}
</style>
