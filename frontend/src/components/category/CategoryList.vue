<template>
  <v-sheet class="d-flex flex-column">
    <v-list nav>
      <v-list-item
        v-for="(category, index) in categories" 
        :key="category.id"
        @click="onClick(category)"
      >
        {{ category.name }}
        <VDivider v-if="index < categories.length - 1"/>
      </v-list-item>
    </v-list>
  </v-sheet>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue';
import { useCategoryStore } from '@/stores/categoryStore';

const emit = defineEmits(['on-click'])
const categoryStore = useCategoryStore();

const onClick = (category) => {
  emit('on-click', category)
}
const categories = computed(() => {
  return categoryStore.categories
})
onMounted(async () => {
  await categoryStore.getCategories();
})
</script>
