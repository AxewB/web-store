<template>
  <VSheet >
    <VSheet v-if="product">
      <VContainer>
        <VRow>
          <VCol>
            <VSheet class="d-flex flex-row">
              <VSheet
                class="d-flex flex-column pa-2"
                rounded
                elevation="5"
              >
                <span class="text-caption text-disabled">
                  Превью
                </span>

                <VSheet
                  width="150"
                  height="150"
                >
                  <ImageContainer
                    :src="product.thumbnail ? product.thumbnail : noImage"
                    :size="150"
                    :actionTitle="!product.thumbnail ? 'Add' : 'Remove'"
                    @on-click="product.thumbnail ? removeThumbnail() : addThumbnail()"
                  />
                </VSheet>
              </VSheet>
              <VDivider
                class="mx-4"
                vertical
              />
              <VSheet
                class="d-flex flex-column pa-2"
                rounded
                elevation="5"
              >
                <span class="text-caption text-disabled">
                  Изображения
                </span>
                  <VSheet
                    class="d-flex flex-row justify-start"
                    width="100%"
                    rounded
                  >
                    <VSheet
                      v-if="product.images"
                      class="d-flex flex-row justify-start overflow-auto"
                      max-width="600px"
                      rounded
                    >
                      <ImageContainer
                        v-for="(image, index) in product.images"
                        :src="image"
                        :size="150"
                        @on-click="removeImage(index)"
                        :class="index > 0 ? 'ml-2' : ''"
                      />
                    </VSheet>
                  <VBtn
                    @click="addImage()"
                    icon="mdi-plus"
                    rounded
                    border
                    height="150"
                    width="150"
                    :class="product.images ? 'ml-2' : ''"
                  />
                  </VSheet>
              </VSheet>
            </VSheet>
          </VCol>
        </VRow>
        <VRow>
          <VCol>
            <VTextField
              v-model="product.name"
              label="Название"
              variant="solo-filled"
              hide-details
            />
          </VCol>
        </VRow>
        <VRow>
          <VCol>
            <VTextarea
              v-model="product.description" 
              hide-details
              label="Описание"
            />
          </VCol>
        </VRow>
        <VRow>
          <VCol>
            <VTextField
              class="mr-2"
              v-model="product.quantity"
              hide-details 
              label="Количество"
              type="number"
            />
          </VCol>
          <VCol>
            <VTextField
            v-model="product.cost"
            hide-details
            label="Стоимость"
            type="number"
          />
          </VCol>
        </VRow>
        <VRow>
          <VCol>
            <VAutocomplete
              v-model="product.categories"
              label="Категории"
              :items="categories"
              item-title="name"
              item-value="id"
              multiple
              clearable
            />
          </VCol>
        </VRow>
      </VContainer>
      <VSheet class="d-flex ">
      </VSheet>
    </VSheet>
    <VSheet
      class="d-flex justify-end"
      width="100%"
    >
      <VBtn @click="closeTab()">
        Отмена
      </VBtn>
      <VBtn
        class="ml-2"
        color="primary"
        @click="finishEditing()"
      >
        Принять
      </VBtn>
    </VSheet>
  </VSheet>
  <OneLineInputOverlay
    :isShown="oneLineInputData.isShown"
    :title="oneLineInputData.title"
    :func="oneLineInputData.func"
    @on-confirm="onOneLineInputConfirm"
    @on-cancel="onOneLineInputCancel"
  />
  <ConfirmDialog
    v-model="isConfirming"
    @on-confirm="confirmEditing"
  />
</template>

<script setup>
import { useProductStore } from '@/stores/productStore';
import { useCategoryStore } from '@/stores/categoryStore';
import { defineProps, onMounted, ref, computed, reactive } from 'vue'
import ImageContainer from '@/components/ImageContainer.vue';
import OneLineInputOverlay from '../OneLineInputOverlay.vue';
import noImage from '@/assets/no-image.png'

// --- service ---

const props = defineProps(['id'])
const emit = defineEmits(['on-close', 'on-confirm'])
const productStore = useProductStore();
const categoryStore = useCategoryStore();

// --- variables ---

const product = reactive({
  "name": '',
  "description": '',
  "thumbnail": '',
  "images": [],
  "quantity": 0,
  "cost": 0,
  "categories": [],
}) // local data for editing
const isConfirming = ref(false)


const oneLineInputData = ref({
  title: '',
  isShown: false,
  func: () => {}
})

// --- computed ---

const categories = computed(() => {
  return categoryStore.getCategoriesNamesAndIds;
})

// --- methods ---

function closeTab() {
  emit('on-close')
}
function finishEditing() {
  isConfirming.value = true;
}
function confirmEditing() {
  emit('on-confirm', {
    id: props.id,
    product
  })
}

function addThumbnail() {
  oneLineInputData.value.isShown = true
  oneLineInputData.value.title = "Enter thumbnail URL"
  oneLineInputData.value.func = putThumbnail;
}
function putThumbnail(url) {
  resetOneLineInput();
  product.thumbnail = url
}
function addImage() {
  oneLineInputData.value.isShown = true
  oneLineInputData.value.title = "Enter image URL"
  oneLineInputData.value.func = putImage;
}

function putImage(url) {
  resetOneLineInput();
  product.images.push(url)
}
function removeThumbnail() {
  product.thumbnail = null;
}

function removeImage(index) {
  product.images.splice(index, 1);
}

function onOneLineInputCancel() {
  resetOneLineInput();
}

function onOneLineInputConfirm() {
  resetOneLineInput();
}

function resetOneLineInput() {
  oneLineInputData.value.isShown = false;
  oneLineInputData.value.title = '';
  oneLineInputData.value.func = () => {};
}

onMounted(async () => {
  if (props.id) {
    const {res} = await productStore.fetchProduct(props.id)
    const data = res.data
    product.name = data.name
    product.description = data.description
    product.thumbnail = data.thumbnail
    product.images = data.images
    product.quantity = data.quantity
    product.cost = data.cost
    product.categories = data.categories
  }
  await categoryStore.getCategories();
})
</script>