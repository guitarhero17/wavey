<script>
  import ContentLoader from 'svelte-content-loader'
  export let animateCL = true
</script>

<li class="w-1/4 m:w-1/3 s:w-1/2 xs:w-full">
  <div class="m-4">
    <ContentLoader width="100%" height="220px" primaryColor="#111827" animate= { animateCL }>
      <rect x="0" y="0" rx="11" ry="11" width="100%" height="159px"/>
      <circle cx="20" cy="195px" r="20" />
      <rect x="150px" y="180px" rx="4" ry="4" width="40%" height="10px"/>
      <rect x="170px" y="200px" rx="4" ry="4" width="30%" height="10px"/>
    </ContentLoader>
  </div>
</li>