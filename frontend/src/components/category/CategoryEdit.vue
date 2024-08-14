<template>
  <VSheet>
    <VSheet>
      <VTextField
        v-model="category.name"
        label="Название"
        hide-details
        class="mb-2"
      />
      <VAutocomplete
        :items="categories"
        v-model="category.parent"
        item-title="name"
        item-value="id"
        label="Родительская категория"
        hide-details
        :disabled="isRoot"
      />
      <VCheckbox
        label="Is root"
        v-model="isRoot"
        hide-details
      />
    </VSheet>
    <VSheet class="d-flex justify-end" width="100%">
      <VBtn @click="emit('on-cancel')">
        Отмена
      </VBtn>
      <VBtn
        class="ml-2"
        @click="confirmEditing()"
        color="primary"
      >
        Принять
      </VBtn>
    </VSheet>
  </VSheet>
</template>

<script setup>
import { computed, defineProps, onMounted, ref, watch, reactive } from 'vue'
import { useCategoryStore } from '@/stores/categoryStore';
const categoryStore = useCategoryStore();
const props = defineProps(['id'])
const emit = defineEmits(['on-close', 'on-confirm'])
const category = reactive({
  "name": '',
  "parent": null,
})

const isRoot = ref(false)
const categories = computed(() => {
  return categoryStore.categories
})

const confirmEditing = () => {
  emit('on-confirm', {
    name: category.name,
    parent: isRoot ? null : category.parent,
  })
}

onMounted(async () => {
  if (props.id) {
    const res = await categoryStore.getCategory(props.id)
    if (res[0]) { // При получении не возникло ошибок
      const categoryData = res[0].response.data
      category.name = categoryData.name
      category.parent = categoryData.parent
      isRoot.value = category.parent === null
    }
  }
})
</script>