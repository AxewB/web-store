<template>
  <VListGroup
    v-if="children.length > 0"
    :value="category.name"
  >
    <template v-slot:activator="{ props }">
      <VListItem
        v-bind="props"
        :title="category.name"
        :value="category"
        @click="categoryClick(category)"
      />
    </template>
    <CatalogueCategoryItem
      v-for="child in children"
      :key="child.id + 'child'"
      :category="child"
      :value="child"
      @click="categoryClick(child)"
    />
  </VListGroup>
  <VListItem
    v-else
    link
    :value="category"
    @click="categoryClick(category)"
  >
    {{ category.name }}
  </VListItem>
</template>


<script setup>
import { onMounted, ref } from 'vue';
import { useCategoryStore } from '@/stores/categoryStore';

// variables

const categoryStore = useCategoryStore();

const emit = defineEmits(['on-category-click'])
const props = defineProps({
  category: {
    type: Object,
    required: true
  }

})
const children = ref({})

// methods

function categoryClick(category) {
  emit('on-category-click', category)
}

// lifecycle hooks

onMounted(async () => {
  const { res } = await categoryStore.getChildren(props.category.id)
  children.value = res.data
})
</script>