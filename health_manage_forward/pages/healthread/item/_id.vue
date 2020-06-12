<template>
  <v-container>
    <v-row>
      <v-col md="7">
        <v-card>
          <v-card-title v-text="item.title"></v-card-title>
          <v-divider></v-divider>
          <v-card-text v-html="item.content"></v-card-text>
        </v-card>
      </v-col>
      <v-col md="4">
        <v-card>
          <v-card-title>热文推荐</v-card-title>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { getArticleById } from "../../../api/item/article";
export default {
  asyncData({ params }) {
    return getArticleById(params.id).then(resp => {
      console.log(resp);
      return {
        item: resp.data
      };
    });
  }
};
</script>

<style scoped></style>
