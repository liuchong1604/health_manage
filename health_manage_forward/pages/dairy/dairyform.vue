<template>
  <v-stepper v-model="step">
    <v-stepper-header>
      <v-stepper-step :complete="step > 1" step="1">选择日期</v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step :complete="step > 2" step="2">健康日志</v-stepper-step>
      <v-divider />
      <v-stepper-step :complete="step > 3" step="3">健康状况</v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step :complete="step > 4" step="4">饮食管理</v-stepper-step>
    </v-stepper-header>
    <v-stepper-items>
      <!-- 1.日期选择 -->
      <v-stepper-content step="1">
        <v-row align-center justify-center>
          <v-col md="1"></v-col>
          <v-col>
            <v-dialog ref="dialog" v-model="dialog" :return-value.sync="date" persistent width="290px">
              <template v-slot:activator="{ on }">
                <v-text-field v-model="date" v-on="on" label="请选择日期" prepend-icon="mdi-application" readonly></v-text-field>
              </template>
              <v-date-picker v-model="date" scrollable>
                <v-spacer></v-spacer>
                <v-btn @click="dialog = false" text color="primary">
                  Cancel
                </v-btn>
                <v-btn @click="$refs.dialog.save(date)" text color="primary">
                  OK
                </v-btn>
              </v-date-picker>
            </v-dialog>
          </v-col>
        </v-row>
      </v-stepper-content>
      <!--2.健康日志  @toggle="toggle" -->
      <v-stepper-content step="2">
        <v-editor v-model="content" upload-url="/upload/image"></v-editor>
      </v-stepper-content>
      <!--3.健康状况 -->
      <v-stepper-content step="3">
        <el-card>
          <div slot="header">
            <span>健康记录</span>
          </div>
          <p></p>
          <el-form ref="healthForm" :model="healthForm" :rules="healthRules" size="medium" label-position="left" label-width="65px" status-icon>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="睡眠(h)" prop="sleep">
                  <el-input v-model="healthForm.sleep" placeholder="0~24" autocomplete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="心率" prop="bs">
                  <el-input v-model="healthForm.bs" placeholder="请输入..." autocomplete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="抽烟(支)" prop="cigarette">
                  <el-input v-model="healthForm.cigarette" placeholder="0~20" autocomplete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="压力值" prop="pressure">
                  <el-input v-model="healthForm.pressure" placeholder="0~20" autocomplete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="体重指数" prop="bmi">
                  <el-input v-model="healthForm.bmi" placeholder="0~20" autocomplete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="血压(mmHg)" prop="bp">
                  <el-input v-model="healthForm.hdp" placeholder="0~20" autocomplete="off">
                    <template slot="prepend">高</template>
                  </el-input>
                  <el-input v-model="healthForm.ldp" placeholder="0~20" autocomplete="off">
                    <template slot="prepend">低</template>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label="心情" prop="mood">
                  <el-input v-model="healthForm.mood" type="textarea"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </v-stepper-content>
      <!--4.饮食管理 -->
      <v-stepper-content step="4">
        <v-card>
          <v-card-text>选择一天所吃的食物</v-card-text>
          <v-divider />
          <p></p>
          <v-chip-group v-model="selected" column multiple>
            <v-chip v-for="(food, index) in foods" :key="index" color="indigo" filter outlined>
              {{ food }}
            </v-chip>
          </v-chip-group>
          <v-card-actions>
            <v-spacer />
            <v-btn @click="submit" color="primary" type="button">提交</v-btn>
          </v-card-actions>
        </v-card>
      </v-stepper-content>
    </v-stepper-items>
  </v-stepper>
</template>

<script>
import { saveDairy, getAllFoods, getFoodsByFid, updateDairy } from "../../api/item/dairy";
import { getToken } from "../../api/auth";
import VEditor from "../../components/editor";
// import qs from "qs";
export default {
  components: {
    VEditor
  },
  props: {
    step: {
      type: Number,
      default: 1
    },
    oldDairys: {
      type: Object,
      default: null
    },
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({
    date: new Date().toISOString().substr(0, 10),
    dialog: false,
    content: "",
    healthForm: {
      sleep: 8,
      bs: 120,
      mood: "好",
      hdp: 120,
      ldp: 110,
      cigarette: 0,
      pressure: 100,
      bmi: 20
    },
    healthRules: {
      sleep: [{ required: true, message: "睡眠时间不为空", trigger: "blur" }],
      bs: [{ required: true, message: "心率不为空", trigger: "blur" }],
      mood: [{ required: true, message: "心情不为空", trigger: "blur" }],
      hdp: [{ required: true, message: "高血压不为空", trigger: "blur" }],
      ldp: [{ required: true, message: "低血压不为空", trigger: "blur" }],
      cigarette: [
        { required: true, message: "抽烟支数不为空", trigger: "blur" }
      ],
      pressure: [{ required: true, message: "压力值不为空", trigger: "blur" }],
      bmi: [{ required: true, message: "体重指数不为空", trigger: "blur" }]
    },
    foods: [],
    selected: [],
    dairys: { // 单个日志数据，命名时名字有误
      dairy: {},
      foods: []
    },
    logined: false,
    user: ""
  }),
  watch: {
    oldDairys: { // 在Vue实例创建时， obj.b 并未声明，因此就没有被Vue转换为响应式的属性，自然就不会触发视图的更新，这时就需要使用Vue的全局api
      deep: true,
      handler (val) {
        if (this.isEdit) {
          console.log("编辑")
          this.dairys.dairy = val.dairy;
          this.dairys.foods = val.foods;
          console.log("编辑：" + this.dairys.dairy.id);
          console.log("编辑：" + this.dairys.foods[0].foodname)
          this.date = this.dairys.dairy.createdtime;
          this.content = this.dairys.dairy.content;
          this.healthForm.sleep = this.dairys.dairy.sleep;
          this.healthForm.bs = this.dairys.dairy.bs;
          this.healthForm.mood = this.dairys.dairy.mood;
          this.healthForm.hdp = this.dairys.dairy.hdp;
          this.healthForm.ldp = this.dairys.dairy.ldp;
          this.healthForm.cigarette = this.dairys.dairy.cigarette;
          this.healthForm.pressure = this.dairys.dairy.pressure;
          this.healthForm.bmi = this.dairys.dairy.bmi;
          const ids = [];
          this.dairys.foods.forEach(el => {
            ids.push(el.id - 1);
          })
          this.selected = ids;
        }
        if (!this.isEdit) {
          console.log("新增");
          console.log(this.isEdit);
          this.dairys.dairy = null;
          this.date = new Date().toISOString().substr(0, 10);
          this.content = null;
          this.dairys.foods = [];
          this.selected = [];
        }
      }
    }
  },
  created () {
    if (getToken()) {
      this.logined = true;
      getAllFoods().then(resp => {
        if (resp.status !== 200) {
          this.$message.error("服务器端出错，未获取到食物");
        } else {
          resp.data.forEach(el => {
            this.foods.push(el.foodname);
          });
        }
      });
      this.$store
        .dispatch("GetUserInfo")
        .then(resp => {
          this.user = resp.data;
        })
        .catch(err => {
          console.log("err: " + err);
        });
    } else {
      this.$message.error("你还未登录，请先登录");
      this.logined = false;
      setTimeout(() => {
        this.$router.push("/login");
      }, 2000);
    }
  },
  methods: {
    // toggle (val) {
    //   this.content = val;
    // },
    async submit () {
      if (this.isEdit) {
        if (this.date !== this.dairys.dairy.createdtime) {
          this.$message.error("编辑时请不要修改日期");
        }
      }
      console.log("123");
      console.log(this.content);
      if (!this.$refs.healthForm.validate()) {
        this.$message.error("请先完成健康状况表");
      }
      if (this.selected.length === 0) {
        this.$message.error("请至少选择一种食物");
      }
      if (this.content === "") {
        this.$message.error("请先输入日志内容");
      } else {
        this.dairys.dairy = null;
        this.dairys.food = [];
        this.dairys.dairy = {
          createdtime: this.date,
          content: this.content,
          sleep: this.healthForm.sleep,
          bs: this.healthForm.bs,
          mood: this.healthForm.mood,
          hdp: this.healthForm.hdp,
          ldp: this.healthForm.ldp,
          cigarette: this.healthForm.cigarette,
          pressure: this.healthForm.pressure,
          bmi: this.healthForm.bmi,
          userId: this.user.id,
          status: false
        };
        // console.log(JSON.stringify(this.dairys));
        const arr = [];
        this.selected.forEach(el => {
          el += 1;
          arr.push(el);
        });
        await getFoodsByFid(arr.join(","))
          .then(resp => {
            if (resp.status === 200) {
              resp.data.forEach(el => {
                this.dairys.food.push({ foodname: el.foodname });
              });
            }
          })
          .catch(err => {
            console.log("err: " + err);
          });
        if (!this.isEdit) {
          await saveDairy(JSON.stringify(this.dairys))
            .then(resp => {
              console.log("123");
              console.log(resp);
              if (resp.status !== 201) {
                this.$message.error("保存失败");
              } else {
                this.selected = [];
                this.date = new Date().toISOString().substr(0, 10);
                this.content = "";
                // 成功，关闭窗口
                this.$emit("close");
                this.$message.success("保存成功");
                location.reload();
              }
            })
            .catch(err => {
              this.$message.error("err: " + err);
            });
        } else {
          await updateDairy(JSON.stringify(this.dairys))
            .then(resp => {
              if (resp.status === 201) {
                this.selected = [];
                this.date = new Date().toISOString().substr(0, 10);
                this.content = "";
                // 成功，关闭窗口
                this.$emit("close");
                this.$message.success("修改成功");
                location.reload();
              }
            })
        }
      }
    }
  }
};
</script>

<style scoped></style>
