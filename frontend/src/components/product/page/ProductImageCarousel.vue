<template>
  <v-sheet class="d-flex flex-column" rounded>
    <v-sheet class="pa-2" rounded 
    color="grey-lighten-1">
      <v-carousel v-model="currentImage" height="350" hide-delimiters show-arrows="hover">
        <v-carousel-item
          v-for="image in props.images"
          :key="image + 'carousel'"
          :src="image"
          border
          rounded/>
      </v-carousel>
    </v-sheet>
    <v-sheet width="100%" class="d-flex justify-center" rounded>
      <v-slide-group
        v-model="currentImage"
        class="pa-4"
        center-active
        show-arrows
      >
        <v-slide-group-item
          v-for="image in props.images"
          :key="image + 'slide-group'"
          v-slot="{ isSelected, toggle }"
        >
          <v-card
            :color="isSelected ? 'primary' : 'grey-lighten-1'"
            class="mx-1"
            height="50"
            width="50"
            @click="toggle"
          >
          <template #image>
            <v-img :src="image"></v-img>
          </template>
          </v-card>
        </v-slide-group-item>
      </v-slide-group>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import { ref } from 'vue'

const currentImage = ref(0)

const props = defineProps({
  images: {
    type: Array,
    required: true
  }
})


</script>