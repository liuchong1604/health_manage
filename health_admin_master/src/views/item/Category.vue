<template>
  <v-layout column justify-center align-center>
    <v-card width="80%">
      <v-card-title>分类管理</v-card-title>
      <v-divider />
      <v-treeview
        :active.sync="active"
        :open.sync="open"
        :items="categoryList"
        :load-children="loadChildren"
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
        <template v-slot:append="{ item }">
          <v-btn @click="deleteItem(item)" v-if="!item.isParent" icon>
            <v-icon small>mdi-delete</v-icon>
          </v-btn>
        </template>
      </v-treeview>
    </v-card>
    <p></p>
  </v-layout>
</template>

<script>
import { getCategoryByPid, deleteCategory } from "../../common/item/category";
export default {
  name: "Category",
  inject: ["reload"],
  data: () => ({
    open: [],
    active: [],
    oldactive: [{ id: 0 }],
    categoryList: [],
    max25chars: v => v.length <= 25 || "Input too long!"
  }),
  created() {
    this.getCategoryList(0);
  },
  methods: {
    getCategoryList(pid) {
      getCategoryByPid(pid)
        .then(resp => {
          if (resp.status !== 200) {
            this.$message.error("获取分类列表失败");
          }
          resp.data.forEach(el => {
            el.label = el.name;
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
    loadChildren(item) {
      return getCategoryByPid(item.id)
        .then(resp => {
          if (resp.status !== 200) {
            this.$message.error("获取分类列表失败");
          }
          resp.data.forEach(el => {
            // el._name = item._name + "," + el.name;
            el.label = el.name;
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
    deleteItem(item) {
      this.$confirm(
        "此操作将永久删除该分类以及该分类下的所有文章, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
          center: true
        }
      )
        .then(() => {
          deleteCategory(item.id).then(resp => {
            if (resp.status !== 201) {
              this.$message.error("删除失败");
            } else {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.reload();
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
