<template>
  <v-layout column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>健康风险评估</v-toolbar-title>
    </v-toolbar>
    <v-row>
      <v-col md="4">
        <v-row>
          <v-col>
            <v-card width="600">
              <v-card-title>相关介绍</v-card-title>
              <v-divider />
              <v-card-text>
                <p class="subtitle-1">
                  &nbsp;&nbsp;心脑血管病风险评估工具提供了一款针对中国人心脑血管病（包括急性心肌梗死、冠心病死亡和脑卒中）发病风险进行评估和健康指导的实用工具，本工具适用于20岁及以上、无心脑血管病史的人群。
                </p>
                <p class="subtitle-1">
                  请在评估表格中输入您的信息，本工具将根据这些信息：
                </p>
                <p class="subtitle-1">
                  1.
                  评估您未来发生心脑血管病的风险和理想危险因素状态下的参考风险；
                </p>
                <p class="subtitle-1">2. 给出心脑血管病发病风险分层，包括</p>
                <p class="subtitle-1">
                  10年风险分层：低危(&lt;5%)、中危（5%-9.9%）或高危（≥10%）
                </p>
                <p class="subtitle-1">
                  3. 针对不同风险分层和单个危险因素水平给出相应提示和建议。
                </p>
                <p class="subtitle-1">
                  本工具基于China-PAR模型研发。如果您进行了注册，将获得更全面的服务。
                </p>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col md="7">
        <v-row>
          <v-col>
            <v-card width="900">
              <v-card-title>风险评估</v-card-title>
              <v-divider />
              <p></p>
              <el-form ref="form" :model="form" :rules="formRules" size="medium" label-position="left" label-width="180px" status-icon>
                <el-form-item label="01. 性别" prop="sex">
                  <el-radio-group v-model="form.sex">
                    <el-radio label="男"></el-radio>
                    <el-radio label="女"></el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="02. 年龄(岁)" prop="age">
                  <el-col :span="8">
                    <el-input v-model.number="form.age" placeholder="20-85" autocomplete="off"></el-input>
                  </el-col>
                </el-form-item>
                <el-form-item label="03. 收缩压(mmHg)" prop="systolic">
                  <el-input-number v-model="form.systolic" placeholder="70-200" controls-position="right"></el-input-number>
                </el-form-item>
                <el-form-item label="04. 体重指数(kg/m^2)" prop="BMI">
                  <el-input-number v-model="form.BMI" placeholder="18-35" controls-position="right"></el-input-number>
                </el-form-item>
                <el-form-item label="05. 总胆固醇(mmol/L)" prop="cholesterol">
                  <el-input-number v-model="form.cholesterol" placeholder="2.85-6.70" controls-position="right"></el-input-number>
                </el-form-item>
                <el-form-item label="06. 是否吸烟" prop="smoke">
                  <el-radio-group v-model="form.smoke">
                    <el-radio label="是"></el-radio>
                    <el-radio label="否"></el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="07. 是否患糖尿病" prop="diabetes">
                  <el-radio-group v-model="form.diabetes">
                    <el-radio label="是"></el-radio>
                    <el-radio label="否"></el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="08. 心脑血管疾病家族史" prop="family">
                  <el-radio-group v-model="form.family">
                    <el-radio label="是"></el-radio>
                    <el-radio label="否"></el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item>
                  <el-col :span="12">
                    <el-button @click="submit" type="primary">
                      立即评测
                    </el-button>
                  </el-col>
                  <el-col :span="8">
                    <el-button @click="resetForm">取消</el-button>
                  </el-col>
                </el-form-item>
              </el-form>
              <p></p>
            </v-card>
          </v-col>
          <v-col>
            <v-card v-if="show">
              <v-card-title>评估结果</v-card-title>
              <v-card-text>
                <p style=" font-size:18px; color:#000; text-align:center; line-height:150%;">
                  您的心脑血管病10年发病风险为
                  <v-avatar color="orange" size="80">
                    <span v-if="result <= -1" class="white--text headline">
                      0.3%
                    </span>
                    <span v-else-if="result <= 3" class="white--text headline">
                      1.1%
                    </span>
                    <span v-else-if="result <= 8" class="white--text headline">
                      5.4%
                    </span>
                    <span v-else-if="result <= 17" class="white--text headline">
                      16.8%
                    </span>
                    <span v-else-if="result > 17" class="white--text headline">
                      ≥52.6%
                    </span>
                  </v-avatar>
                </p>
                <p style=" font-size:18px; color:#000; text-align:center; line-height:150%;">
                  理想危险因素状态下,您的心脑血管病10年发病风险应小于
                  <v-avatar color="indigo" size="80">
                    <span v-if="ideaResult <= 3" class="white--text headline">
                      5.4%
                    </span>
                    <span v-if="ideaResult <= 5" class="white--text headline">
                      16.8%</span>
                    <span v-if="ideaResult > 5" class="white--text headline">
                      21.7%
                    </span>
                  </v-avatar>
                </p>
                <p><span style=" color:#ce3e3e;">注：</span></p>
                <p>
                  <span style=" color:#a2a2a2;">
                    理想危险因素状态：总胆固醇 &lt; 200 mg/dl(5.18
                    mmol/L)，高密度脂蛋白胆固醇 ≥ 40 mg/dl(1.04
                    mmol/L)，未治疗的收缩压 &lt; 120 mmHg，舒张压 &lt;
                    80mmHg，腰围男性 &lt; 90cm、女性 &lt;
                    85cm，当前不吸烟，没有糖尿病，无心脑血管病家族史。年龄、性别和现居住地区使用用户输入的数值</span>
                </p>
                <p style="margin-top:8px;">
                  <span style=" color:#a2a2a2;">
                    风险划分标准：10年风险：低危（&lt;5%）、中危（5%-9.9%）或高危（≥10%）;</span>
                </p>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <p></p>
  </v-layout>
</template>

<script>
import { getResult } from "../../api/item/evaluate";
export default {
  data () {
    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("年龄不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 20) {
            callback(new Error("需要大于20岁"));
          } else if (value > 85) {
            callback(new Error("年龄需要小于85岁"));
          }
          callback();
        }
      }, 1000);
    };
    const checkSystolic = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("收缩压不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 80) {
            callback(new Error("收缩压必须大于80"));
          } else if (value > 400) {
            callback(new Error("收缩压必须小于400"));
          }
          callback();
        }
      }, 1000);
    };
    const checkBMI = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("体重指数不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 18) {
            callback(new Error("体重指数必须大于18"));
          } else if (value > 35) {
            callback(new Error("收缩压必须小于35"));
          }
          callback();
        }
      }, 1000);
    };
    const checkcholesterol = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("总胆固醇数值不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 2.85) {
            callback(new Error("数值必须大于2.85"));
          } else if (value > 6.7) {
            callback(new Error("数值必须小于6.7"));
          }
          callback();
        }
      }, 1000);
    };
    return {
      form: {
        sex: "男",
        age: "",
        systolic: 100, // 收缩压
        BMI: 20,
        cholesterol: 3, // 总胆固醇
        smoke: "否",
        diabetes: "否", // 糖尿病
        family: "否"
      },
      formRules: {
        sex: [{ required: true, message: "性别不能为空" }],
        age: [
          { required: true, message: "年龄不能为空" },
          { validator: checkAge, trigger: "blur" }
        ],
        systolic: [
          { required: true, message: "收缩压不能为空" },
          { validator: checkSystolic, trigger: "blur" }
        ],
        BMI: [
          { required: true, message: "体重指数不能为空" },
          { validator: checkBMI, trigger: "blur" }
        ],
        cholesterol: [
          { required: true, message: "数值不能为空" },
          { validator: checkcholesterol, trigger: "blur" }
        ],
        smoke: [{ required: true, message: "该项不能为空" }],
        diabetes: [{ required: true, message: "该项不能为空" }],
        family: [{ required: true, message: "该项不能为空" }]
      },
      show: false,
      result: 0,
      ideaResult: 0
    };
  },
  methods: {
    resetForm () {
      this.$refs.form.resetFields();
    },
    submit () {
      this.$refs.form.validate(valid => {
        if (valid) {
          const formData = {
            sex: this.form.sex,
            age: this.form.age,
            systolic: this.form.systolic,
            BMI: this.form.BMI,
            cholesterol: this.form.cholesterol
          };
          if (this.form.sex === "男") {
            this.ideaResult += 2;
          } else if (this.form.sex === "女") {
            this.ideaResult += 1;
          }
          if (this.form.age <= 39) {
            this.ideaResult += 0;
          } else if (this.form.age > 39 && this.form.age <= 44) {
            this.ideaResult += 1;
          } else if (this.form.age > 44 && this.form.age <= 49) {
            this.ideaResult += 2;
          } else if (this.form.age > 49 && this.form.age <= 54) {
            this.ideaResult += 3;
          } else if (this.form.age > 54) {
            this.ideaResult += 4;
          }
          if (this.form.smoke === "是") {
            formData.smoke = true;
          } else {
            formData.smoke = false;
          }
          if (this.form.diabetes === "是") {
            formData.diabetes = true;
          } else {
            formData.diabetes = false;
          }
          if (this.form.family === "是") {
            formData.family = true;
          } else {
            formData.family = false;
          }

          getResult(this.$qs.stringify(formData))
            .then(resp => {
              if (resp.status !== 200) {
                this.$message.error("评测失败,请再次尝试");
              }
              this.show = true;
              this.result = resp.data;
            })
            .catch(err => {
              console.log("err: " + err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    }
  }
};
</script>

<style scoped></style>
