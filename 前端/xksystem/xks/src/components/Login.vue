<template>
  <v-container>
    <v-snackbar
      v-model="account_failed"
      multi-line="multi-line"
      :top="y === 'top'"
      :timeout="timeout"
    >
      账号或密码错误！
      <v-btn color="red" text @click="account_failed = false">关闭</v-btn>
    </v-snackbar>
    <v-card class="elevation-12">
      <v-toolbar color="indigo" dark flat>
        <v-toolbar-title>选课系统</v-toolbar-title>
        <v-spacer/>
      </v-toolbar>
      <v-card-text>
        <v-form>
          <v-text-field label="账号" name="account" type="number" v-model="account"/>
          <v-text-field id="password" label="密码" name="password" type="password" v-model="pwd"/>
          <v-select
            :items="user_type_array"
            label="用户类型"
            :value="user_type"
            @change="change_user_type($event)"
          ></v-select>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer/>
        <v-btn color="indigo" style="color:white;margin-right:20px;" @click="cancel()">取消</v-btn>
        <v-btn color="indigo" style="color:white;" @click="login()">登录</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    account: "",
    pwd: "",
    account_failed: false,
    y: "top",
    timeout: 2000,
    user_type_array: ["学生", "老师", "管理员"],
    user_type: "学生"
  }),
  created() {
    if (
      localStorage.getItem("id") != null &&
      localStorage.getItem("password") != null
    ) {
      this.account = localStorage.getItem("id");
      this.pwd = localStorage.getItem("password");
    }
  },
  methods: {
    login() {
      if (this.user_type == "学生") {
        this.$http({
          method: "post",
          url: "/api/student/students/login/",
          data: {
            sid: this.account,
            password: this.Base64.encode(this.pwd)
          }
        })
          .then(res => {
            //登录成功
            if (res.data) {
              localStorage.setItem("id", this.account);
              localStorage.setItem("password", this.pwd);
              localStorage.setItem("token", res.data.token);
              this.clearInput();
              this.$emit("login", res.data.token);
            } else {
              this.account_failed = true;
            }
          })
          .catch(error => {
            console.log(error.response);
          });
      }
      else if(this.user_type == "老师"){
        this.$http({
          method: "post",
          url: "/api/teacher/teachers/login/",
          data: {
            tid: this.account,
            password: this.Base64.encode(this.pwd)
          }
        })
          .then(res => {
            //登录成功
            if (res.data) {
              localStorage.setItem("id", this.account);
              localStorage.setItem("password", this.pwd);
              localStorage.setItem("token", res.data.token);
              this.clearInput();
              this.$emit("login", res.data.token);
            } else {
              this.account_failed = true;
            }
          })
          .catch(error => {
            console.log(error.response);
          });
      }
      else if(this.user_type == "管理员"){
        this.$http({
          method: "post",
          url: "/api/sysadmin/systemadmins/login/",
          data: {
            adminid: this.account,
            password: this.Base64.encode(this.pwd)
          }
        })
          .then(res => {
            //登录成功
            if (res.data) {
              localStorage.setItem("id", this.account);
              localStorage.setItem("password", this.pwd);
              localStorage.setItem("token", res.data.token);
              this.clearInput();
              this.$emit("login", res.data.token);
            } else {
              this.account_failed = true;
            }
          })
          .catch(error => {
            console.log(error.response);
          });
      }
    },
    change_user_type(e) {
      this.user_type = e;
    },
    cancel() {
      this.clearInput();
      this.$emit("cancel");
    },
    clearInput() {
      this.account = "";
      this.pwd = "";
    }
  }
};
</script>