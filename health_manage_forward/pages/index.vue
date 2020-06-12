<template>
  <v-layout column relative justify-center align-center>
    <v-card class="mx-auto" flat relative width="2000px">
      <v-carousel
        :show-arrows="false"
        cycle
        hide-delimiter-background
        delimiter-icon="mdi-minus"
        height="450"
      >
        <v-carousel-item
          v-for="(item, i) in items"
          :key="i"
          :src="item.src"
        ></v-carousel-item>
      </v-carousel>
      <p></p>
      <img width="5%" />
      <img src="~assets/img/four.png" width="90%" height="450px" />
      <p></p>
    </v-card>
  </v-layout>
</template>

<script>
export default {
  name: "Layout",
  components: {},
  data() {
    return {
      items: [
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg"
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/plane.jpg"
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/bird.jpg"
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg"
        }
      ]
    };
  }
};
</script>
