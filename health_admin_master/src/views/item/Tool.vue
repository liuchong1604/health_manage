<template>
  <v-layout column justify-center align-center>
    <v-card width="80%">
      <v-card-title>
        工具管理
        <v-spacer />
        <v-btn color="primary" @click="addTool">新增工具</v-btn>
      </v-card-title>
      <v-divider />
      <v-data-table
        :headers="headers"
        :items="tools"
        :loading="loading"
        loading-text="Loading... Please wait"
        hide-default-footer
      >
        <template v-slot:item.path="{ item }">
          <img :src="item.path" alt="" height="50" />
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
        <v-card-text style="height:400px">
          <tool-form
            @close="closeWindow"
            :oldTool="oldTool"
            :isEdit="isEdit"
          ></tool-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
import { getTools } from "../../common/item/tool";
import ToolForm from "views/item/ToolForm";
export default {
  name: "Tool",
  components: {
    ToolForm
  },
  data: () => ({
    headers: [
      {
        text: "id",
        align: "start",
        value: "id"
      },
      {
        text: "名称",
        align: "center",
        sortable: false,
        value: "name"
      },
      {
        text: "工具介绍",
        align: "center",
        sortable: false,
        value: "introduce"
      },
      {
        text: "工具图片",
        align: "center",
        sortable: false,
        value: "path"
      },
      {
        text: "操作",
        align: "center",
        sortable: false,
        value: "actions"
      }
    ],
    tools: [],
    oldTool: {}, // 即将被编辑的工具数据
    isEdit: false,
    show: false,
    loading: true
  }),
  created() {
    this.getToolsFromServer();
  },
  methods: {
    getToolsFromServer() {
      getTools()
        .then(resp => {
          if (resp.status === 200) {
            this.tools = resp.data;
            this.loading = false;
          }
        })
        .catch(err => {
          console.log("err: " + err);
        });
    },
    addTool() {
      this.isEdit = false;
      this.show = true;
      this.oldTool = null;
    },
    closeWindow() {
      this.getToolsFromServer();
      // 关闭窗口
      this.show = false;
    },
    editItem(item) {
      this.isEdit = true;
      this.show = true;
      this.oldTool = item;
    },
    deleteItem(item) {
      console.log(item);
    }
  }
};
</script>

<style scoped></style>
