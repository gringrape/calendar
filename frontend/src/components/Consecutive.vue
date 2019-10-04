<template>
  <div class="event-consecutive event-start event-end" 
    v-popover:pop
    :data-span="period"
    @click="$emit('clicked', {'id': id, 'sort': sort})"
    >
    {{ title }}
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Consecutive',
  props: {
    id: String,
    title: String,
    period: Number,
    sort: Number
  },
  data() {
    return {

    }
  },
  methods: {
  }
}
</script>

<style>
</style>
