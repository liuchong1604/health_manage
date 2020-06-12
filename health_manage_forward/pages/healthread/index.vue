<template>
  <v-layout column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>健康阅读</v-toolbar-title>
    </v-toolbar>
    <p></p>
    <v-row>
      <v-col md="1"></v-col>
      <v-col md="7">
        <v-card width="800">
          <v-card-title>
            健康小知识
            <v-spacer></v-spacer>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="输入关键字搜索"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-data-table
            :headers="headers"
            :items="articles"
            :options.sync="options"
            :server-items-length="totalArticle"
            :search="search"
            :loading="loading"
            loading-text="Loading... Please wait"
            class="elevation-1"
          >
            <template v-slot:item.title="{ item }">
              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-chip v-on="on" @click="handleClick(item)" draggable>
                    {{ item.title }}
                  </v-chip>
                </template>
                <span>点击查看详细内容</span>
              </v-tooltip>
              <!-- <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <a
                    @click="handleClick(item)"
                    v-on="on"
                    href="javascript:void(0);"
                  >
                    {{ item.name }}
                  </a>
                </template>
                <span>点击查看详细内容</span>
              </v-tooltip> -->
            </template>
            <template v-slot:item.created="{ item }">
              {{ item.created }}
            </template>
          </v-data-table>
        </v-card>
      </v-col>
      <v-col md="4">
        <v-card width="450">
          <v-card-title>阅读分类</v-card-title>
          <v-divider></v-divider>
          <v-treeview
            :active.sync="active"
            :open.sync="open"
            :items="categoryList"
            :load-children="loadChildren"
            @update:active="updateActive"
            color="info"
            return-object
            activatable
            transition
            item-key="id"
            open-on-click
            dense
          >
            <template v-slot:prepend="{ item, open }">
              <v-icon v-if="item.isParent">
                {{ open ? "mdi-folder-open" : "mdi-folder" }}
              </v-icon>
              <v-icon v-if="!item.isParent">
                mdi-bookmark
              </v-icon>
            </template>
          </v-treeview>
        </v-card>
        <v-card>
          <v-card-title>阅读分类</v-card-title>
        </v-card>
      </v-col>
    </v-row>
    <p></p>
  </v-layout>
</template>

<script>
import { getCategoryByPid } from "../../api/item/category";
import { getArticlesByCid, getArticles } from "../../api/item/article";
import { formatData } from "../../api/common";

export default {
  data: () => ({
    logined: false,
    search: "",
    headers: [
      {
        text: "id",
        value: "id"
      },
      {
        text: "标题",
        align: "start",
        sortable: false,
        value: "title"
      },
      { text: "发布日期", value: "created" }
    ],
    open: [],

    active: [],
    oldactive: [{ id: 0 }],
    categoryList: [],
    selected: {
      id: 1,
      item: []
    },
    options: {
      page: 1,
      itemsPerPage: 10,
      sortBy: [],
      sortDesc: []
    },
    articles: [],
    totalArticle: 0,
    loading: true
  }),
  watch: {
    // active: {
    //   // 点击同一个两次会出错,待解决
    //   deep: true,
    //   handler() {
    //     if (this.active.length !== 0) {
    //       if(this.oldactive[0].id)
    //       // console.log(this.active);
    //       this.selected.id = this.active[0].id;
    //       const items = this.active[0]._name.split(",");
    //       // console.log(items);
    //       const item = items[items.length - 1];
    //       // console.log(item);
    //       this.selected.item = item;
    //       // console.log(this.selected);
    //       this.oldactive = this.active;
    //       console.log(this.oldactive);
    //     }
    //   }
    // },
    options: {
      deep: true,
      handler() {
        this.getArticlesFromSever();
      }
    },
    search: {
      // 监视搜索字段
      handler() {
        this.getArticlesFromSever();
      }
    }
  },
  created() {
    this.getData();
  },
  methods: {
    async getData() {
      await this.getCategoryList(0);
      await this.getArticlesFromSever();
    },
    getCategoryList(pid) {
      getCategoryByPid(pid)
        .then(resp => {
          if (resp.status !== 200) {
            this.$message.error("获取分类列表失败");
          }
          resp.data.forEach(el => {
            el._name = el.name;
            if (el.isParent) {
              el.children = [];
            }
          });
          this.categoryList = resp.data;
        })
        .catch(err => {
          console.log("err: " + err);
        });
    },
    updateActive() {
      if (this.active.length !== 0) {
        if (this.oldactive[0].id !== this.active[0].id) {
          this.oldactive = this.active;
          this.getArticles(this.active[0].id);
        }
      }
    },
    loadChildren(item) {
      return getCategoryByPid(item.id)
        .then(resp => {
          if (resp.status !== 200) {
            this.$message.error("获取分类列表失败");
          }
          resp.data.forEach(el => {
            el._name = item._name + "," + el.name;
            if (el.isParent) {
              el.children = [];
            }
          });
          item.children.push(...resp.data);
        })
        .catch(err => {
          console.log("err: " + err);
        });
    },
    getArticlesFromSever() {
      const key = this.search;
      const page = this.options.page;
      const rows = this.options.itemsPerPage;
      let sortBy = null;
      if (this.options.sortBy.length === 1) {
        sortBy = this.options.sortBy[0];
      }

      let desc = null;
      if (this.options.sortDesc.length === 1) {
        desc = this.options.sortDesc[0];
      }

      getArticles(key, page, rows, sortBy, desc).then(resp => {
        if (resp.status !== 200) {
          this.$message.error("获取文章失败");
        }
        this.articles = resp.data.items;
        this.articles.forEach(el => {
          el.created = formatData(el.created);
        });
        this.totalArticle = resp.data.total;
        this.loading = false;
      });
    },
    getArticles(cid) {
      getArticlesByCid(cid)
        .then(resp => {
          if (resp.status !== 200) {
            this.$message.error("获取该分类文章失败");
          }
          this.articles = resp.data;
          this.articles.forEach(el => {
            el.created = formatData(el.created);
          });
        })
        .catch(err => {
          console.log("err: " + err);
        });
    },
    handleClick(item) {
      location.href = "/healthread/item/" + item.id;
    }
  }
};
</script>

<style scoped></style>
