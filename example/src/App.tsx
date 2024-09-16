import { useState, useEffect } from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { requestOverlayPermission } from '@vokhuyet/react-native-draw-overlay';

export default function App() {
  const [grant, setGrant] = useState<boolean>();

  useEffect(() => {
    requestOverlayPermission().then(setGrant);
  }, []);

  return (
    <View style={styles.container}>
      <Text>grant: {grant}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
