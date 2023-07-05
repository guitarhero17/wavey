const playerTypesMap =  new Map()
playerTypesMap.set('DRUMS', 'Drummers')
playerTypesMap.set('GUITAR', 'Guitarists')
playerTypesMap.set('BASS', 'Bassists')
playerTypesMap.set('KEYS', 'Keyboard players')
playerTypesMap.set('VOCALS', 'Singers')
playerTypesMap.set('CELLO', 'Cellists')
playerTypesMap.set('SAXOPHONE', 'Saxophonists')
playerTypesMap.set('TRUMPET', 'Trumpeters')
playerTypesMap.set('VIOLIN', 'Violinists')

export default function getPlayerType(instrument) {
  return playerTypesMap.get(instrument) ?? 'Some players to jam with'
}