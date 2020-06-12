<template>
  <v-app>
    <!-- 左侧导航条 -->
    <v-navigation-drawer
      v-model="drawer"
      :dark="dark"
      :mini-variant="miniVariant"
      fixed
      app
    >
      <v-toolbar flat class="transparent">
        <v-btn icon></v-btn>
        <v-list class="pa-0">
          <v-list-item>
            <v-list-item-avatar>
              <v-img src="../assets/img/2.jpeg"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>虎哥</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-toolbar>
      <v-divider />
      <!-- 左侧菜单 -->
      <v-list dense class="pt-0">
        <v-list-group
          v-for="(item, i) in items"
          :key="i"
          :prepend-icon="item.action"
          no-action
        >
          <!-- 一级菜单 -->
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
          </template>
          <!-- 二级菜单 -->
          <v-list-item
            v-for="subItem in item.items"
            :key="subItem.title"
            :to="item.path + subItem.path"
          >
            <v-list-item-title>{{ subItem.title }}</v-list-item-title>
            <v-list-item-icon>
              <v-icon>{{ subItem.action }}</v-icon>
            </v-list-item-icon>
          </v-list-item>
        </v-list-group>
      </v-list>
    </v-navigation-drawer>

    <!-- 顶部工具条 -->
    <v-app-bar :color="dark ? 'secondary' : 'primary'" app dark>
      <!-- 隐藏左侧菜单的按钮 -->
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <!--隐藏左侧菜单的按钮 -->
      <v-btn icon @click.stop="miniVariant = !miniVariant">
        <v-icon
          v-html="miniVariant ? 'mdi-chevron-right' : 'mdi-chevron-left'"
        ></v-icon>
      </v-btn>
      <v-switch
        :label="dark ? '暗黑' : '明亮'"
        v-model="dark"
        color="dark"
        hide-details
      />
      <v-flex xs3></v-flex>
      <v-toolbar-title v-text="title"></v-toolbar-title>
      <v-spacer />
      <v-menu open-on-hover offset-y>
        <template v-slot:activator="{ on }">
          <v-btn v-on="on" color="primary" depressed large>
            <v-icon>mdi-account-box</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item @click="1">
            <v-list-item-title>
              个人信息
            </v-list-item-title>
          </v-list-item>
          <v-list-item @click="1">
            <v-list-item-title>
              修改密码
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <v-content>
      <v-breadcrumbs :items="links">
        <template v-slot:divider>
          <v-icon>mdi-chevron-right</v-icon>
        </template>
      </v-breadcrumbs>
      <div><router-view v-if="isRouterAlive"></router-view></div>
    </v-content>
  </v-app>
</template>

<script>
import menus from "@/menu";
export default {
  name: "App",
  data: () => ({
    dark: false, // 是否是暗黑主题
    drawer: true, // 左侧导航条是否隐藏
    miniVariant: false, // 左侧导航条是否收起
    title: "智检后台管理系统",
    menuMap: {},
    isRouterAlive: true
  }),
  provide() {
    return {
      reload: this.reload
    };
  },
  computed: {
    items() {
      return menus;
    },
    links() {
      const arrl = this.$route.path.split("/");
      const item1 = this.menuMap[arrl[1]].name;
      const item2 = this.menuMap[arrl[1]][arrl[2]];
      const arr = [
        {
          text: item1,
          disable: true,
          href: "#"
        },
        {
          text: item2,
          disabled: true
        }
      ];
      return arr;
    },
    item1() {
      const arr = this.$route.path.split("/");
      return this.menuMap[arr[1]].name;
    },
    item2() {
      const arr = this.$route.path.split("/");
      return this.menuMap[arr[1]][arr[2]];
    }
  },
  created() {
    menus.forEach(m => {
      const pl = m.path.slice(1);
      this.menuMap[pl] = { name: m.title };
      m.items.forEach(i => {
        this.menuMap[pl][i.path.slice(1)] = i.title;
      });
    });
  },
  methods: {
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function() {
        this.isRouterAlive = true;
      });
    }
  }
};
</script>

<style scoped></style>
