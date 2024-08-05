<template>
  <v-list>
    <v-sheet 
      v-for="category in categories" 
      :key="category.id" 
    >
    <CatalogueCategoryItem 
      :category="category" 
      @on-category-click="onCategoryClick"
      v-if="category.parent === null"
    />
    </v-sheet>
    
  </v-list>
</template>

<script setup>
import {onMounted, computed } from 'vue';
import { useCategoryStore } from '@/stores/categoryStore';
import CatalogueCategoryItem from './CatalogueCategoryItem.vue';

const emit = defineEmits(['on-category-click'])

const categoryStore = useCategoryStore();
const categories = computed(() => {
  return categoryStore.categories
})

function onCategoryClick(category) {
  emit('on-category-click', category)
}

onMounted(() => {
  categoryStore.getCategories()
})
</script>