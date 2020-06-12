<template>
  <v-layout v-if="logined" column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>个人信息</v-toolbar-title>
      <v-spacer />
    </v-toolbar>
    <p></p>
    <v-card class="mx-auto" width="450">
      <el-form ref="form" :model="form" :rules="formRules" label-width="80px" class="demo-ruleForm">
        <el-col :span="18">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" :disabled="true" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" :disabled="true" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="resource">
            <el-radio-group v-model="form.sex">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="出生日期" required>
            <el-form-item prop="date">
              <el-date-picker v-model="form.date" type="date" placeholder="选择日期" style="width: 100%;" format="yyyy-MM-dd" value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="submit('form')" type="primary">提交</el-button>
            <el-button @click="resetForm('form')">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </v-card>
    <p></p>
  </v-layout>
</template>

<script>
import { getToken } from '../../api/auth'
import { findByUid, modifyUser } from '../../api/user'
import { formatData } from '../../api/common'
export default {
  name: 'App',
  data () {
    const validateEmail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入邮箱'));
      } setTimeout(() => {
        const emailRules = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
        if (!emailRules.test(value)) {
          callback(new Error("邮箱格式不合法"))
        } else {
          callback();
        }
      }, 1000);
    };
    return {
      valid: true,
      logined: false,
      uid: "",
      form: {
        username: "",
        phone: "",
        sex: "男",
        date: "",
        email: ""
      },
      formRules: {
        sex: [
          { required: true, message: '请选择性别', trigger: 'blur' }
        ],
        date: [
          { required: true, message: '请选择日期', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: validateEmail, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    if (getToken()) {
      this.logined = true;
      this.getData();
    } else {
      this.logined = false;
      this.$message.error("你还未登录，请先登录");
      setTimeout(() => {
        this.$router.push("/login");
      }, 2000);
    }
  },
  methods: {
    async getData () {
      await this.$store
        .dispatch("GetUserInfo")
        .then(resp => {
          this.uid = resp.data.id;
        })
        .catch(err => {
          console.log("err: " + err);
        });
      await findByUid(this.uid)
        .then(resp => {
          this.form.username = resp.data.username;
          this.form.phone = resp.data.phone;
          console.log(resp.data.sex);
          if (resp.data.sex === null) {
            this.form.sex = "男"
          } else {
            this.form.sex = resp.data.sex;
          }
          if (resp.data.birth === null) {
            this.form.date = "";
          } else {
            this.form.date = formatData(resp.data.birth).substring(0, 10);
          }
          this.form.email = resp.data.email;
        })
        .catch(err => {
          console.log("err: " + err);
        })
    },
    submit (formName) {
      if (getToken()) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const formData = {
              sex: this.form.sex,
              birth: this.form.date,
              email: this.form.email,
              uid: this.uid
            }
            modifyUser(this.$qs.stringify(formData))
              .then(resp => {
                if (resp.status === 200) {
                  this.$message.success("修改成功");
                  location.reload();
                }
              })
              .catch(err => {
                console.log("err： " + err);
              })
          }
        })
      } else {
        this.logined = false;
        this.$message.error("你还未登录，请先登录");
        setTimeout(() => {
          this.$router.push("/login");
        }, 2000);
      }
    },
    resetForm (formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
</style>
