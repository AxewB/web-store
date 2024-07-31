<template>
  <v-sheet >
    <v-sheet v-if="product">
      <v-container>
        <v-row>
          <v-col>
            <v-sheet class="d-flex flex-row">
              <v-sheet class="d-flex flex-column pa-2" rounded elevation="5">
                <span class="text-caption text-disabled">Thumbnail</span>
                  <v-sheet width="150" height="150">
                    <ImageContainer 
                      :src="product.thumbnail ? product.thumbnail : noImage"
                      :size="150"
                      :actionTitle="!product.thumbnail ? 'Add' : 'Remove'"
                      @on-click="product.thumbnail ? removeThumbnail() : addThumbnail()"
                    />
                  </v-sheet>
              </v-sheet>
              <VDivider vertical class="mx-4"/>
              <v-sheet class="d-flex flex-column pa-2" rounded elevation="5">
                <span class="text-caption text-disabled">Images</span>
                  <v-sheet class="d-flex flex-row justify-start" width="100%" rounded>
                    <v-sheet v-if="product.images" class="d-flex flex-row justify-start overflow-auto" max-width="600px" rounded>
                      <ImageContainer
                      v-for="(image, index) in product.images"
                      :src="image"
                      :size="150"
                      @on-click="removeImage(index)"
                      :class="index > 0 ? 'ml-2' : ''"/>
                    </v-sheet>
                  <v-btn
                    @click="addImage()"
                    icon="mdi-plus"
                    rounded
                    border
                    height="150"
                    width="150"
                    :class="product.images ? 'ml-2' : ''"
                  />
                  </v-sheet>
              </v-sheet>
            </v-sheet>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-text-field 
              v-model="product.name"
              label="name"
              variant="solo-filled"
              hide-details/>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-textarea hide-details label="description" v-model="product.description"/>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-text-field hide-details class="mr-2" label="quantity" type="number" v-model="product.quantity"/>
          </v-col>
          <v-col>
            <v-text-field hide-details label="cost" type="number" v-model="product.cost"/>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-autocomplete
              v-model="product.categories"
              label="Categories"
              :items="categories"
              item-title="name"
              item-value="id"
              multiple
              clearable
            ></v-autocomplete>
          </v-col>
        </v-row>
      </v-container>
      <v-sheet class="d-flex ">
      </v-sheet>
    </v-sheet>
    <v-sheet class="d-flex justify-end" width="100%">
      <v-btn @click="closeTab()">
        Cancel
      </v-btn>
      <v-btn
        @click="finishEditing()" 
        class="ml-2" 
        color="primary">
        Confirm
      </v-btn>
    </v-sheet>
  </v-sheet>
  <OneLineInputOverlay 
    :isShown="oneLineInputData.isShown"
    :title="oneLineInputData.title"
    :func="oneLineInputData.func"
    @on-confirm="onOneLineInputConfirm"
    @on-cancel="onOneLineInputCancel"
  />
  <ConfirmDialog v-model="isConfirming" @on-confirm="confirmEditing"/>

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
    const res = await productStore.getProduct(props.id)
    product.name = res.name
    product.description = res.description
    product.thumbnail = res.thumbnail
    product.images = res.images
    product.quantity = res.quantity
    product.cost = res.cost
    product.categories = res.categories
  }
  await categoryStore.getCategories();
})
</script>