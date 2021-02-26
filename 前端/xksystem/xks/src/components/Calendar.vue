<template>
  <div>
    <div class="score-title">总学分: {{sum_score}}</div>
    <v-data-table :headers="headers" :items="courses" :items-per-page="10">
      <template v-slot:item.action="{ item }">
        <v-btn @click="delete_item(item)" color="primary">退课</v-btn>
      </template>
    </v-data-table>
  </div>
</template>

<script>
export default {
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
  data: () => ({
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
    courses: [],
    sum_score:""
  }),
  created() {
    this.get_choose_courses();
  },
  methods: {
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
              this.courses.splice(index, 1);
            }
          })
          .catch(error => {
            console.log(error.response);
          });
      }
    },
    get_choose_courses() {
      if (this.sid == null) return;
      this.$http({
        method: "get",
        url: "/api/student/get_chosen_courses/",
        headers: {
          "Authorization": this.token
        },
      })
        .then(res => {
          let sum=0;
          for (var k = 0; k < res.data.length; k++) {
            var pn = res.data[k].ocid.choosenum + "/" + res.data[k].ocid.maxnum;
            var c = res.data[k].ocid.cid.cname + "  " + res.data[k].ocid.cid.cid;
            var t = res.data[k].ocid.tid.tname + "  " + res.data[k].ocid.tid.tid;
            var xname=res.data[k].ocid.xid.xname;
            this.$set(this.courses, k, {
              pnum: pn,
              course: c,
              teacher: t,
              time: res.data[k].ocid.time,
              place: "["+xname + "] " + res.data[k].ocid.place,
              score: res.data[k].ocid.cid.score,
              chosen:true,
              ocid:res.data[k].ocid.id,
            });
            sum+=parseInt(res.data[k].ocid.cid.score);
          }
          this.sum_score=sum;
        })
        .catch(error => {
          console.log(error.response);
        });
    }
  }
};
</script>

<style scoped>
.score-title{
  font-size: 14px;
  margin-bottom: 20px;
  margin-left: 14px;
  color: rgba(0, 0, 0, 0.8);
}
</style>
