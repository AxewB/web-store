<template>
  <VSheet>
    <TabActionRow  title="Добавить категорию">
      <CategoryEdit @on-confirm="confirmAdding"/>
    </TabActionRow>
  </VSheet>
</template>

<script setup>
import TabActionRow from '@/components/user/tabs/TabAction.vue';
import CategoryEdit from '@/components/category/CategoryEdit.vue';
import { useCategoryStore } from '@/stores/categoryStore';

const categoryStore = useCategoryStore();

const confirmAdding = async (category) => {
  await categoryStore.addCategory(category)
  await categoryStore.getCategories();
}
</script>