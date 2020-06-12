<template>
  <v-app class="wraper">
    <!-- v-app>>v-content>>v-container » v-layout » v-flex. -->
    <el-backtop target=".wraper">
      <!--必须是父类第一个组件并且父类class为wrapper下有wrapper的css -->
      <v-btn dark fab color="pink">
        <v-icon>mdi-chevron-up</v-icon>
      </v-btn>
    </el-backtop>
    <header>
      <v-system-bar color="red lighten-5">
        <v-spacer></v-spacer>
        <div v-if="!logined">
          <v-btn @click="click2" text small>
            <nuxt-link to="/test">帮助</nuxt-link>
          </v-btn>
          <v-btn @click="click1" text small>
            <nuxt-link to="/login">登录</nuxt-link>
          </v-btn>
        </div>
        <div v-else>
          <v-btn text small> 欢迎你,<span v-text="username">张三</span> </v-btn>
          <v-btn @click="click2" text small>
            <nuxt-link to="/test">帮助</nuxt-link>
          </v-btn>
          <v-btn @click="1" text small>
            <nuxt-link to="/profile">个人信息</nuxt-link>
          </v-btn>
          <v-btn @click="1" text small>
            <nuxt-link to="/changepw">修改密码</nuxt-link>
          </v-btn>
          <v-btn @click="logout" text small>
            <nuxt-link to="/">退出</nuxt-link>
          </v-btn>
        </div>
      </v-system-bar>
      <v-app-bar color="red lighten-5" flat relative>
        <a href="/">
          <img src="~assets/img/design.png" height="75" width="110" />
        </a>
        <v-toolbar color="red lighten-5" flat>
          <v-toolbar-title>
            <nuxt-link to="/" class="a">
              智检平台
            </nuxt-link>
          </v-toolbar-title>
        </v-toolbar>
        <v-spacer />
        <!--选项卡出现问题,直接输入url生成的slider默认在网站首页上,不知道怎么解决 -->
        <v-tabs v-model="currentItem" hide-slider optional background-color="red lighten-5" right active-class="active">
          <v-tab href="/">
            网站首页
          </v-tab>
          <v-menu bottom left offset-y>
            <template v-slot:activator="{ on }">
              <v-btn v-on="on" text class="align-self-center mr-4" height="64">
                <span style="font-size: 0.875rem;font-weight: 500;">
                  我的健康
                </span>
                <v-icon right>mdi-menu-down</v-icon>
              </v-btn>
            </template>

            <v-list>
              <v-list-item v-for="(item, index) in items" :key="index" @click="handleClick(item)">
                <v-list-item-title>{{ item.title }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
          <v-tab href="/healthread">
            健康阅读
          </v-tab>
          <v-tab href="/tools">健康工具</v-tab>
          <v-tab href="/about">关于我们</v-tab>
        </v-tabs>
      </v-app-bar>
    </header>
    <v-content>
      <nuxt />
      <!-- <v-tab-item value="tab-healthread"><health-read /></v-tab-item>
        <v-tab-item value="tab-tool"><tool /></v-tab-item> -->
    </v-content>
    <v-footer relative padless>
      <v-card color="primary" flat tile width="100%">
        <v-row>
          <v-col cols="4">
            <v-list two-line flat color="primary">
              <v-list-item>
                <v-list-item-icon> </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>友情赞助</v-list-item-title>
                  <v-divider dark />
                  <v-list-item-subtitle inset>
                    <br />
                    你的支持会让我们越来越好.
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-col>
          <v-col cols="4">
            <v-list two-line flat color="primary">
              <v-list-item>
                <v-list-item-icon> </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>联系我们</v-list-item-title>
                  <v-divider dark />
                  <v-list-item-subtitle inset>
                    <br />
                    北京市丰台区西四环南路101号创新大厦5层.
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    电话：86-10-6379 9983.
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    传真：86-10-6211 3665.
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    邮编：100070.
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    邮箱：
                    <a href="mailto:wellsunny@wellcare.cn" style="color: #e4e4e4;">
                      wellsunny@wellcare.cn </a>.
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    网址：
                    <a href="www.wellcare.cn" style="color: #e4e4e4;">
                      www.wellcare.cn </a>.
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-col>
          <v-col cols="4">
            <v-list two-line flat color="primary">
              <v-list-item>
                <v-list-item-icon> </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>关于我们</v-list-item-title>
                  <v-divider dark />
                  <v-list-item-subtitle>
                    <br />
                    <p class="text--primary">
                      健康管理服务将使您获得以下益处:
                    </p>
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    <p class="text--primary">
                      1 完善个人健康数据库，了解个人健康状况.
                    </p>
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    <p class="text--primary">
                      2 建立健康预警及行动干预系统.
                    </p>
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    <p class="text--primary">
                      3 随时查阅自己健康资料、获得健康改善的行动指导.
                    </p>
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-col>
          <v-card-text class="text-center">
            <v-divider />
            Copyright &copy;2019 - {{ new Date().getFullYear() }} 智检平台
            版权所有 All Rights Reserved By CoderLc.
          </v-card-text>
          <!-- <v-col class="text-center" cols="12">
            <v-divider />
            Copyright ©2019 - {{ new Date().getFullYear() }} All Rights Reserved
            By CoderLc.
          </v-col> -->
        </v-row>
      </v-card>
    </v-footer>
  </v-app>
</template>

<script>
import { getToken } from "../api/auth";
// import HealthRead from "../pages/healthread/index";
// import Tool from "../pages/tools/index";

export default {
  // components: {
  //   HealthRead,
  //   Tool
  // { title: "心理测评" },},
  data () {
    return {
      logined: false,
      fab: false,
      items: [
        { title: "健康监测" },
        { title: "疾病评估" },
        { title: "膳食推荐" },
        { title: "健康日志" }
      ],
      username: "",
      hide: false,
      tabs: null,
      currentItem: "tab-home",
      oldItem: "",
      user: ""
    };
  },
  created () {
    if (getToken()) {
      this.logined = true;
      this.$store
        .dispatch("GetUserInfo")
        .then(resp => {
          this.user = resp.data;
          this.username = this.user.username;
        })
        .catch(err => {
          console.log("err: " + err);
        });
    }
  },
  // mounted() {
  //   const data = sessionStorage.getItem("oldItem");
  //   this.currentItem = data;
  //   let url;
  //   url = data.substring(4);
  //   history.pushState("", "", url);
  // },
  methods: {
    click1 () {
      location.href = "/login";
    },
    click2 () {
      location.href = "/test";
    },
    click (data) {
      this.hide = false;
      history.pushState("", "", data);
    },
    logout () {
      this.$store
        .dispatch("Logout")
        .then(() => {
          console.log("退出成功");
          location.href = "/";
        })
        .catch(err => {
          console.log(err);
        });
    },
    handleClick (item) {
      console.log(item.title);
      if (item.title === "健康日志") {
        location.href = "/dairy";
      }
      if (item.title === "膳食推荐") {
        location.href = "/diet";
      }
      if (item.title === "疾病评估") {
        location.href = "/evaluate";
      }
      if (item.title === "健康监测") {
        location.href = "/examination";
      }
    }
  },
  head () {
    return {
      title: "智检平台"
    };
  }
};
</script>

<style scoped>
@import "../assets/base.css";
.a {
  text-decoration: none;
  color: #000000;
}
.wraper {
  height: 100vh;
  overflow-x: hidden;
}
</style>
