<template>
  <StickeyHeader></StickeyHeader>
  <div>
    <div class="container-fluid">
      <div class="row">
        <SideBar></SideBar>
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
          <h2>テストページ1</h2>
          <router-link to="/about">次のページ</router-link>
        </main>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { defineComponent } from 'vue'
import StickeyHeader from './StickeyHeader.vue'
import SideBar from './SideBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"

export default defineComponent({
  data() {
    return {
    }
  },
  components: {
    StickeyHeader,
    SideBar
  }
})

</script>