<template>
  <v-layout column justify-center align-center>
    <v-card width="80%">
      <v-card-title>
        <v-btn color="primary" @click="addItem">新增文章</v-btn>
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
        </template>
        <template v-slot:item.actions="{ item }">
          <v-btn @click="editItem(item)" icon>
            <v-icon small>mdi-pencil</v-icon>
          </v-btn>
          <v-btn @click="deleteItem(item)" icon>
            <v-icon small>
              mdi-delete
            </v-icon>
          </v-btn>
        </template>
      </v-data-table>
    </v-card>
    <p></p>
    <v-dialog max-width="500" v-model="show" persistent scrollable>
      <v-card>
        <v-toolbar dense dark color="primary">
          <v-toolbar-title>{{ isEdit ? "修改" : "新增" }}工具</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon @click="closeWindow"><v-icon>mdi-close</v-icon></v-btn>
        </v-toolbar>
        <v-card-text class="px-3">
          <news-form
            :oldArticle="oldArticle"
            :step="step"
            @close="closeWindow"
            :isEdit="isEdit"
            ref="articleForm"
          ></news-form>
        </v-card-text>
        <v-card-actions class="elevation-10">
          <v-row>
            <v-col md="2"></v-col>
            <v-col md="5">
              <v-btn @click="previous" :disabled="step === 1" color="primary">
                上一步
              </v-btn>
            </v-col>
            <v-col md="5">
              <v-btn @click="next" :disabled="step === 2" color="primary">
                下一步
              </v-btn>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
import NewsForm from "views/item/NewsForm";
export default {
  name: "News",
  components: {
    NewsForm
  },
  data: () => ({
    search: "",
    loading: true,
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
      { text: "发布日期", value: "created" },
      { text: "操作", sortable: false, value: "actions" }
    ],
    articles: [],
    oldArticle: {},
    options: {
      page: 1,
      itemsPerPage: 10,
      sortBy: [],
      sortDesc: []
    },
    totalArticle: 0,
    loading: true,
    isEdit: false,
    show: false,
    step: 1
  }),
  watch: {
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
  methods: {
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
    addItem() {
      this.isEdit = false;
      this.show = true;
      this.oldArticle = null;
    },
    handleClick(item) {
      console.log(item);
    },
    editItem(item) {
      console.log(item);
    },
    deleteItem(item) {
      console.log(item);
    },
    closeWindow() {
      // 关闭窗口
      this.show = false;
    },
    previous() {
      if (this.step > 1) {
        this.step--;
      }
    },
    next() {
      if (this.step < 2) {
        this.step++;
      }
    }
  }
};
</script>

<style scoped></style>
