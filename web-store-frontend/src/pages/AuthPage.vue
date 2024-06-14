<template>
  <div>
    <v-sheet 
      height="100vh" 
      width="100vw"
      class="d-flex 
            justify-center 
            align-center 
            flex-column
            bg-transparent">
        
        <v-sheet
          class="pa-4 rounded d-flex flex-column"
          width="400px">
          <span class="text-h4">
            Login
          </span>
            <divider class="ma-2"/>
          <v-text-field
            label="Login"/>
          <v-text-field
            label="Password"/>
          <v-btn variant="tonal" size="large">Login</v-btn>
        </v-sheet>
    </v-sheet>
  </div>
</template>

<script setup>

</script>
