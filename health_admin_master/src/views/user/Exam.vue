<template>
  <v-layout column justify-center align-center>
    <v-card width="80%">
      <v-card-title>体检管理</v-card-title>
      <v-data-table
        :headers="headers"
        :items="exams"
        :loading="loading"
        loading-text="Loading... Please wait"
        hide-default-footer
        disable-sort
        class="elevation-1"
      >
        <template v-slot:item.actions="{ item }">
          <v-btn @click="deleteItem(item)" icon>
            <v-icon small>
              mdi-delete
            </v-icon>
          </v-btn>
        </template>
      </v-data-table>
    </v-card>
  </v-layout>
</template>

<script>
import { findAll, deleteExam } from "../../common/user/exam";
import { formatData } from "../../common/common";
export default {
  name: "Exam",
  data: () => ({
    headers: [
      {
        text: "编号",
        align: "start",
        value: "id"
      },
      {
        text: "创建时间",
        value: "examedtime"
      },
      {
        text: "用户id",
        value: "userId"
      },
      {
        text: "操作",
        value: "actions"
      }
    ],
    exams: [],
    loading: true
  }),
  created() {
    this.getExamsFromServer();
  },
  methods: {
    getExamsFromServer() {
      findAll().then(resp => {
        if (resp.status !== 200) {
          this.$message.error("获取所有体检失败");
        }
        resp.data.forEach(el => {
          el.examedtime = formatData(el.examedtime).substring(0, 10);
        });
        this.exams = resp.data;
        this.loading = false;
      });
    },
    deleteItem(item) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true
      })
        .then(() => {
          deleteExam(item.id).then(resp => {
            if (resp.status !== 200) {
              this.$message.error("删除失败，请重试");
            } else {
              this.$message.success("删除成功");
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  }
};
</script>

<style scoped></style>
