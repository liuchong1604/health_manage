<template>
  <v-form v-model="valid" ref="toolForm">
    <v-row>
      <v-col md="1"></v-col>
      <v-col md="8">
        <v-text-field
          v-model="tool.name"
          color="deep-purple"
          label="请输入工具名称"
          filled
          required
          :rules="nameRules"
        />
        <v-textarea
          v-model="tool.introduce"
          color="deep-purple"
          label="工具介绍"
          rows="1"
          :counter="100"
          :rules="introduceRules"
          auto-grow
          filled
        ></v-textarea>
        <v-row>
          <v-col md="12">
            <span style="font-size: 16px; color: #444">工具图片</span>
          </v-col>
          <v-col md="12">
            <v-upload
              v-model="tool.path"
              url="/upload/image"
              :multiple="false"
              :pic-width="250"
              :pic-height="90"
            >
            </v-upload>
          </v-col>
        </v-row>
        <v-btn @click="submit" color="primary" class="mr-12">提交</v-btn>
        <v-btn @click="clear" class="mr-12">重置</v-btn>
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
import VUpload from "components/Upload";
export default {
  name: "ToolForm",
  components: {
    VUpload
  },
  props: {
    oldTool: {
      type: Object,
      default: null
    },
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({
    valid: false,
    tool: {
      name: "",
      introduce: "",
      path: ""
    },
    nameRules: [
      v => !!v || "工具名称不能为空",
      v => (!!v && v.length > 1) || "工具名称至少2位"
    ],
    introduceRules: [
      v => !!v || "工具介绍不能为空",
      v => (!!v && v.length <= 100) || "Max 100 characters"
    ]
  }),
  methods: {
    submit() {
      console.log("提交");
    },
    clear() {
      this.$refs.toolForm.reset();
    }
  }
};
</script>

<style scoped></style>
