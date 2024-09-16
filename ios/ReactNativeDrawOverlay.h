
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNReactNativeDrawOverlaySpec.h"

@interface ReactNativeDrawOverlay : NSObject <NativeReactNativeDrawOverlaySpec>
#else
#import <React/RCTBridgeModule.h>

@interface ReactNativeDrawOverlay : NSObject <RCTBridgeModule>
#endif

@end
