<template>
  <VSheet class="d-flex flex-column">
    <VList nav>
      <VListItem
        v-for="(category, index) in categories"
        :key="category.id"
        @click="onClick(category)"
      >
        {{ category.name }}
        <VDivider v-if="index < categories.length - 1"/>
      </VListItem>
    </VList>
  </VSheet>
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
