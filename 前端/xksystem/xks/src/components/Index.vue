<template>
  <v-app id="index">
    <v-navigation-drawer v-model="drawer" app>
      <v-list dense>
        <v-list-item link @click="panel(0)">
          <v-list-item-action>
            <v-icon>mdi-home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>首页</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link @click="panel(1)">
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>选课信息</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link @click="panel(2)">
          <v-list-item-action>
            <v-icon>mdi-domain</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>成绩管理</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app color="indigo" dark>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"/>
      <v-toolbar-title>{{title}}</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-title class="login-btn" @click="login_dialog=true" v-show="!login_flag">登录</v-toolbar-title>
      <v-toolbar-title class="login-btn" @click="logout()" v-show="login_flag">注销</v-toolbar-title>
      <v-dialog v-model="login_dialog" width="500">
        <Login @login="login" @cancel="cancel"></Login>
      </v-dialog>
    </v-app-bar>

    <v-content style="padding:20px;">
      <ChooseCourse v-if="panel_flag==0" :sid="sid" :token="token"></ChooseCourse>
      <Calendar v-if="panel_flag==1" :sid="sid" :token="token"></Calendar>
      <Performance v-if="panel_flag==2" :sid="sid" :token="token"></Performance>
    </v-content>
    <v-footer color="indigo" app>
      <span class="white--text">&copy;上海大学 2020</span>
    </v-footer>
  </v-app>
</template>

<script>
import ChooseCourse from "./ChooseCourse";
import Login from "./Login";
import Calendar from "./Calendar"
import Performance from "./Performance";

export default {
  name: "Index",
  props: {
    source: String
  },
  components: {
    ChooseCourse,
    Login,
    Calendar,
    Performance
  },
  data: () => ({
    token:"",
    drawer: null,
    login_dialog: false,
    panel_flag:0,
    login_flag:false,
    sid:"",
    title:"选课系统"
  }),
  created() {
    if(localStorage.getItem("token") != null){
      this.token="Token "+localStorage.getItem("token");
      this.login(localStorage.getItem("token"));
    }
  },
  methods: {
    panel(i){
      this.panel_flag=i;
      if(i==0)
        this.title="选课系统"
      else if(i==1)
        this.title="选课信息"
      else if(i==2)
        this.title="成绩管理"
    },
    login(e){
      //login success
      if(e!=""){
        this.login_dialog=false;
        this.login_flag=true;
        this.sid=localStorage.getItem("id");
        this.token="Token " + e;
      }
    },
    cancel(){
      this.login_dialog=false;
    },
    logout(){
      if(confirm('确定要注销?')) {
        this.login_flag=false;
        this.sid="";
        this.token="";
        localStorage.removeItem("id");
        localStorage.removeItem("password");
        localStorage.removeItem("token");
      }
    }
  },
};
</script>

<style scoped>
.login-btn {
  margin-right: 20px;
  cursor: pointer;
}
</style>
