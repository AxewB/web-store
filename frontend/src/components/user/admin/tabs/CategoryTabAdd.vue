<template>
  <v-sheet>
    <TabActionRow  title="Add category">
      <CategoryEdit @on-confirm="confirmAdding"/>
    </TabActionRow>
  </v-sheet>
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