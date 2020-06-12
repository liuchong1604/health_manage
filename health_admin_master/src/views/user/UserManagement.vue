<template>
  <v-layout column justify-center align-center>
    <p></p>
    <v-card width="80%">
      <v-card-title>
        用户管理
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="输入用户名搜索"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="users"
        :options.sync="options"
        :server-items-length="totalUser"
        :search="search"
        :loading="loading"
        loading-text="Loading... Please wait"
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
import { getUsers, deleteUser } from "../../common/user/user";
export default {
  name: "UserManagement",
  data: () => ({
    headers: [
      {
        text: "编号",
        align: "start",
        value: "id"
      },
      {
        text: "用户名",
        value: "username"
      },
      {
        text: "操作",
        value: "actions"
      }
    ],
    // desserts: [
    //   {
    //     id: 12,
    //     username: "tom"
    //   }
    // ],
    options: {
      page: 1,
      itemsPerPage: 10,
      sortBy: [],
      sortDesc: []
    },
    users: [],
    totalUser: 0,
    loading: true,
    search: ""
  }),
  created() {
    this.getUsersFromServer();
  },
  watch: {
    options: {
      deep: true,
      handler() {
        this.getUsersFromServer();
      }
    },
    search: {
      // 监视搜索字段
      handler() {
        this.getUsersFromServer();
      }
    }
  },
  methods: {
    deleteItem(item) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true
      })
        .then(() => {
          deleteUser(item.id).then(resp => {
            if (resp.status !== 200) {
              this.$message.error("删除失败");
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
    },
    getUsersFromServer() {
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

      getUsers(key, page, rows, sortBy, desc).then(resp => {
        if (resp.status !== 200) {
          this.$message.error("获取用户信息失败");
        }
        this.users = resp.data.items;
        this.totalUser = resp.data.total;
        this.loading = false;
      });
    }
  }
};
</script>

<style scoped></style>
