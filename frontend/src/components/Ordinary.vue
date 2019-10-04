<template>
  <div class="event event-start event-end" 
    v-popover:pop
    :data-span="period"
    @click="$emit('clicked', {'id': id, 'sort': sort})"
    > 
    {{ title }}
  </div>
</template>

<script>
export default {
  name: "Ordinary",
   props: {
    id: String,
    title: String,
    period: Number,
    sort: Number
  }
}
</script>