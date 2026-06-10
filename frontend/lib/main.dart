import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'presentation/screens/register_screen.dart';
import 'presentation/screens/home_screen.dart';
import 'package:flutter_frontend/data/services/auth_service.dart';
import 'package:provider/provider.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  try {
    await GoogleSignIn.instance.initialize(
      clientId: '151027052858-08494bo94us88tfssoena94bgst8g4m4.apps.googleusercontent.com', 
      serverClientId: '151027052858-mmdiucsj3s8ddkmfk0rieanmhojts1np.apps.googleusercontent.com',
    );
  } catch (e) {
    debugPrint("GoogleSignIn init error: $e");
  }
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => AuthService()),
      ],
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sitlly App',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.red,
        scaffoldBackgroundColor: const Color(0xFFF9F9F9),
      ),
      home: Consumer<AuthService>(
        builder: (context, auth, _) {
          if (auth.isLoading) {
            return const Scaffold(body: Center(child: CircularProgressIndicator()));
          }
          return auth.isAuthenticated ? const HomeScreen() : const RegisterScreen();
        },
      ),
    );
  }
}
