<template>
  <div id="v-pills-vue" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-vue-tab">
    <div class="list-group--inline py-2">
      <label for="vue-with-style" class="list-group-item gap-3">
        <input
          id="vue-with-style"
          v-model="isVueWithStyle"
          type="checkbox"
          name="vue-with-style"
          class="form-check-input flex-shrink-0"
          value=""
        />
        <span class="form-checked-content">
          <strong>Add Vue.js style</strong>
        </span>
      </label>
    </div>
    <GeneratorButtonVue
      :label="'Generate Vue.js'"
      :service="isVueWithStyle ? 'vue-styled' : 'vue'"
      :selector-prefix="selectorPrefix"
      @click.prevent="addVue"
    />
  </div>
</template>

<script lang="ts" src="./VueGenerator.component.ts"></script>
