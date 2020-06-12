<template>
  <v-layout column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>健康工具</v-toolbar-title>
    </v-toolbar>
    <v-card height="30px" width="100%" flat></v-card>
    <v-container fluid>
      <v-row>
        <v-col md="1"></v-col>
        <v-col md="10">
          <v-row>
            <v-col v-for="card in cards" :key="card.title" md="4">
              <v-hover v-slot:default="{ hover }" open-delay="200">
                <v-card :elevation="hover ? 12 : 2" height="280" outlined>
                  <v-list-item>
                    <v-list-item-avatar></v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title v-text="card.name" class="headline">
                        <!-- <nuxt-link to="#">{{ card.title }}</nuxt-link> -->
                      </v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                  <v-row dense>
                    <v-col md="5"></v-col>
                    <v-col md="2">
                      <v-img :src="card.path" height="50"></v-img>
                    </v-col>
                    <v-col md="5"></v-col>
                  </v-row>

                  <v-card-text v-text="card.introduce"> </v-card-text>

                  <v-overlay v-if="hover" absolute color="cyan lighten-5">
                    <v-btn @click="handleClick(card)" color="primary">
                      立即试用?
                    </v-btn>
                  </v-overlay>
                </v-card>
              </v-hover>
            </v-col>
          </v-row>
        </v-col>
        <v-col md="1"></v-col>
      </v-row>
    </v-container>
    <v-card height="30px" width="100%" flat></v-card>
  </v-layout>
</template>

<script>
import { getCards } from "../../api/item/tools";

export default {
  data: () => ({
    logined: false,
    cards: []
  }),
  created() {
    this.getItem();
  },
  methods: {
    getItem() {
      getCards().then(resp => {
        if (resp.status !== 200) {
          this.$message.error("获取工具列表失败");
        }
        this.cards = resp.data;
      });
    },
    handleClick(card) {
      console.log(card);
      location.href = "tools/tool/" + card.id;
    }
  }
};
</script>

<style scoped></style>
