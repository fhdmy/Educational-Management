<template>
  <div>
    <div class="top-title">
      <div class="top-item"> 总学分: {{sum_score}}</div>
      <div class="top-item"> 平均成绩: {{avg_score}}</div>
      <div style="clear:both;"></div>
    </div>
    <v-data-table :headers="headers" :items="courses" :items-per-page="10">
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
        text: "课程",
        value: "course"
      },
      {
        text: "教师",
        value: "teacher"
      },
      {
        text: "学分",
        value: "score"
      },
      {
        text: "平时成绩",
        value: "pscore"
      },
      {
        text: "考试成绩",
        value: "kscore"
      },
      {
        text: "总评成绩",
        value: "zscore"
      }
    ],
    courses: [],
    sum_score:"",
    avg_score:""
  }),
  created() {
    this.get_choose_courses();
  },
  methods: {
    get_choose_courses() {
      if (this.sid == null) return;
      this.$http({
        method: "get",
        url: "/api/student/get_course_score/",
        headers: {
          "Authorization": this.token
        },
      })
        .then(res => {
          let score_sum=0;
          let zscore_sum=0;
          for (var k = 0; k < res.data.length; k++) {
            var c = res.data[k].ocid.cid.cname + "  " + res.data[k].ocid.cid.cid;
            var t = res.data[k].ocid.tid.tname + "  " + res.data[k].ocid.tid.tid;
            this.$set(this.courses, k, {
              course: c,
              teacher: t,
              score: res.data[k].ocid.cid.score,
              ocid:res.data[k].ocid.id,
              pscore:res.data[k].pscore,
              kscore:res.data[k].kscore,
              zscore:res.data[k].zscore
            });
            score_sum+=parseInt(res.data[k].ocid.cid.score);
            zscore_sum+=parseInt(res.data[k].zscore);
          }
          this.sum_score=score_sum;
          if(res.data.length==0)
            this.avg_score=0;
          else{
            this.avg_score=(zscore_sum/res.data.length).toFixed(1);
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
.top-title{
  font-size: 14px;
  margin-bottom: 20px;
  margin-left: 14px;
  color: rgba(0, 0, 0, 0.8);
}
.top-item{
  float: left;
  width: 120px;
  margin-right: 10px;
}
</style>
