<template>
  <v-layout v-if="logined" column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>我的健康日志</v-toolbar-title>
    </v-toolbar>
    <p></p>
    <v-row dense>
      <v-col md="8">
        <v-card width="800" outlined>
          <v-data-table :headers="headers" :items="dairys" :loading="loading" loading-text="Loading... Please wait" disable-pagination disable-sort hide-default-footer>
            <template v-slot:top>
              <v-toolbar flat color="white">
                <v-toolbar-title>日志记录</v-toolbar-title>
                <v-divider class="mx-4" inset vertical></v-divider>
                <v-spacer></v-spacer>
                <v-btn @click="addItem" color="primary" dark class="mb-2">
                  新增记录
                </v-btn>
                <v-dialog v-model="show" max-width="600px">
                  <!-- <v-card>
                    <v-card-title>
                      <span class="headline">{{ formTitle }}</span>
                    </v-card-title>
                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="editedItem.name"
                              label="Dessert name"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="editedItem.calories"
                              label="Calories"
                            ></v-text-field>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card-text>
                  </v-card> -->
                  <v-card>
                    <!--对话框的标题-->
                    <v-toolbar dense dark color="primary">
                      <v-toolbar-title>
                        {{ isEdit ? "修改" : "新增" }}记录
                      </v-toolbar-title>
                      <v-spacer />
                      <!--关闭窗口的按钮-->
                      <v-btn @click="closeWindow" icon>
                        <v-icon>mdi-close</v-icon>
                      </v-btn>
                    </v-toolbar>
                    <!--对话框的内容，表单-->
                    <v-card-text>
                      <dairy-form ref="dairyForm" :step="step" :oldDairys="oldDairys" :isEdit="isEdit" @close="closeWindow"></dairy-form>
                    </v-card-text>
                    <!--底部按钮，用来操作步骤线-->
                    <v-card-actions class="elevation-10">
                      <v-row>
                        <v-col md="2"></v-col>
                        <v-col md="5">
                          <v-btn @click="previous" :disabled="step === 1" color="primary">
                            上一步
                          </v-btn>
                        </v-col>
                        <v-col md="5">
                          <v-btn @click="next" :disabled="step === 4" color="primary">
                            下一步
                          </v-btn>
                        </v-col>
                      </v-row>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-toolbar>
            </template>
            <template v-slot:item.name="{ item }">
              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-chip v-on="on" @click="handleClick(item)" draggable>
                    {{ item.name }}
                  </v-chip>
                </template>
                <span>点击查看详细内容</span>
              </v-tooltip>
            </template>

            <template v-slot:item.actions="{ item }">
              <v-btn @click="editItem(item)" icon>
                <v-icon small class="mr-2">
                  mdi-pencil
                </v-icon>
              </v-btn>
              <v-btn @click="deleteItem(item)" icon>
                <v-icon small>
                  mdi-delete
                </v-icon>
              </v-btn>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
      <v-col md="4">
        <v-card width="450" outlined>
          <v-card-title>日志搜索</v-card-title>
          <v-divider />
          <v-menu ref="menu" v-model="menu" :close-on-content-click="false" :return-value.sync="dates" transition="scale-transition" offset-y min-width="290px">
            <template v-slot:activator="{ on }">
              <v-container>
                <v-combobox v-model="dates" v-on="on" @click:append-outer="click" multiple chips small-chips label="请选择日期,选择完成后点击搜索按钮查找" prepend-icon="mdi-application" append-outer-icon="mdi-magnify" readonly>
                  <template v-slot:selection="{ item, parent, selected }">
                    <v-chip :input-value="selected" small>
                      <span class="pr-2">
                        {{ item }}
                      </span>
                      <v-icon @click="parent.selectItem(item)" small>
                        mdi-close
                      </v-icon>
                    </v-chip>
                  </template>
                </v-combobox>
              </v-container>
            </template>
            <v-date-picker v-model="dates" multiple no-title scrollable>
              <!-- locale="zh-cn" -->
              <v-spacer></v-spacer>
              <v-btn @click="menu = false" text color="primary">取消</v-btn>
              <v-btn @click="$refs.menu.save(dates)" text color="primary">
                确定
              </v-btn>
            </v-date-picker>
          </v-menu>
        </v-card>
      </v-col>
    </v-row>
    <p />
  </v-layout>
</template>

<script>
import {
  getAllByUid,
  getDairy,
  getFoods,
  deleteDairy,
  getByTimes
} from "../../api/item/dairy";
import { formatData } from "../../api/common";
import { getToken } from "../../api/auth";
import DairyForm from "./dairyform";
// import qs from "qs";
export default {
  components: {
    DairyForm
  },
  data: () => ({
    headers: [
      {
        text: "记录日期",
        align: "start",
        sortable: false,
        value: "createdtime"
      },
      { text: "状态", value: "status" },
      { text: "Actions", value: "actions" }
    ],
    desserts: [
      {
        createdtime: "Frozen Yogurt",
        status: 159
      },
      {
        createdtime: "Ice cream sandwich",
        status: 237
      },
      {
        createdtime: "Eclair",
        status: 262
      }
    ],
    dairys: [], // 当前页面日志数据
    oldDairys: {}, // 即将被编辑的日志数据
    did: "",
    show: false,
    loading: true,
    isEdit: false,
    step: 1, // 子组件中的步骤线索引，默认为1
    menu: false,
    dates: [new Date().toISOString().substr(0, 10)],
    logined: false,
    user: ""
  }),
  created () {
    if (getToken()) {
      this.logined = true;
      this.getDairys();
    } else {
      this.$message.error("你还未登录，请先登录");
      this.logined = false;
      setTimeout(() => {
        this.$router.push("/login");
      }, 2000);
    }
  },
  methods: {
    async getDairys () {
      await this.$store
        .dispatch("GetUserInfo")
        .then(resp => {
          this.user = resp.data;
        })
        .catch(err => {
          console.log("err: " + err);
        });
      await getAllByUid(this.user.id)
        .then(resp => {
          if (resp.status !== 200) {
            this.$message.error("搜索日志失败");
          }
          resp.data.forEach(el => {
            el.createdtime = formatData(el.createdtime).substring(0, 10);
            if (el.status) {
              el.status = "已读";
            } else {
              el.status = "未读";
            }
          });
          this.dairys = resp.data;
          this.loading = false;
        })
        .catch(err => {
          console.log("err:" + err);
        });
    },
    handleClick (item) {
      console.log("查看日志详情页面");
    },
    async editItem (item) {
      await getDairy(item.createdtime)
        .then(resp => {
          if (resp.status === 200) { // 只能监听到一开始赋值对象里面的属性。
            this.$set(this.oldDairys, 'dairy', {});
            this.$set(this.oldDairys, 'dairy', resp.data);
          }
        })
      await getFoods(this.oldDairys.dairy.id)
        .then(resp => {
          if (resp.status === 200) {
            this.$set(this.oldDairys, "foods", {});
            this.$set(this.oldDairys, "foods", resp.data);
          }
        })
      this.isEdit = true;
      this.show = true;
    },
    closeWindow () {
      // 关闭窗口
      this.show = false;
      // 将步骤调整到1
      this.step = 1;
    },
    previous () {
      if (this.step > 1) {
        this.step--;
      }
    },
    next () {
      if (this.step < 4) {
        this.step++;
      }
    },
    addItem () {
      // 修改标记
      this.isEdit = false;
      // 控制弹窗可见：
      this.show = true;
      this.oldDairys = {}
    },
    deleteItem (item) {
      this.$confirm("此操作将永久删除该日志, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteDairy(item.createdtime).then(resp => {
            if (resp.status !== 201) {
              this.$message.error("删除失败,请重新尝试");
            } else {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              // 删除后刷新页面
              setTimeout(() => {
                location.reload();
              }, 2000);
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
      // this.$router.go(0);
    },
    click () {
      this.loading = true;
      getByTimes(this.dates.join())
        .then(resp => {
          if (resp.status !== 200) {
            this.$message.error("未找到所需日志");
          } else {
            resp.data.forEach(el => {
              el.createdtime = formatData(el.createdtime).substring(0, 10);
              if (el.status) {
                el.status = "已读";
              } else {
                el.status = "未读";
              }
            });
            this.dairys = resp.data;
            this.loading = false;
            this.$message.success("查找成功");
          }
        })
        .catch(err => {
          console.log("err: " + err);
        });
    }
  }
};
</script>

<style scoped></style>
