<template>
  <VList>
    <VSheet
      v-for="category in categories"
      :key="category.id"
    >
    <CatalogueCategoryItem
      :category="category"
      @on-category-click="onCategoryClick"
      v-if="category.parent === null"
    />
    </VSheet>
  </VList>
</template>

<script setup>
import {onMounted, computed } from 'vue';
import { useCategoryStore } from '@/stores/categoryStore';
import CatalogueCategoryItem from './CatalogueCategoryItem.vue';

// variables

const emit = defineEmits(['on-category-click'])

const categoryStore = useCategoryStore();
const categories = computed(() => {
  return categoryStore.categories
})

// methods

function onCategoryClick(category) {
  emit('on-category-click', category)
}

// lifecycle hooks

onMounted(() => {
  categoryStore.getCategories()
})
</script>