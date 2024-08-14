<template>
  <VSheet
    v-if="props.images"
    class="d-flex flex-column" 
    rounded
  >
    <VSheet
      class="pa-2"
      rounded
      color="grey-lighten-1"
    >
      <VCarousel
        v-model="currentImage"
        height="350"
        hide-delimiters
        :show-arrows="props.images.length > 1 ? 'hover' : false"
      >
        <VCarouselItem
          v-for="image in props.images"
          :key="image + 'carousel'"
          :src="image"
          border
          rounded
        />
      </VCarousel>
    </VSheet>
    <VSheet
      class="d-flex justify-center"
      width="100%"
      rounded
    >
      <VSlideGroup
        v-model="currentImage"
        class="pa-4"
        center-active
        show-arrows
      >
        <VSlideGroupItem
          v-for="image in props.images"
          :key="image + 'slide-group'"
          v-slot="{ isSelected, toggle }"
        >
          <VCard
            :color="isSelected ? 'primary' : 'grey-lighten-1'"
            class="mx-1"
            height="50"
            width="50"
            @click="toggle"
          >
            <template #image>
              <VImg :src="image"/>
            </template>
          </VCard>
        </VSlideGroupItem>
      </VSlideGroup>
    </VSheet>
  </VSheet>
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