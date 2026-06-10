import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../../core/constants/api_constants.dart';

class FavoriteService {
  final String _baseUrl = '${ApiConstants.baseUrl}/api/favorites';

  Future<String?> _getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('jwt_token');
  }

  Future<List<dynamic>> getFavorites() async {
    final token = await _getToken();
    final response = await http.get(
      Uri.parse(_baseUrl),
      headers: {
        'Content-Type': 'application/json',
        if (token != null) 'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      return json.decode(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Failed to load favorites');
    }
  }

  Future<void> addFavorite(String productId, String size, String color) async {
    final token = await _getToken();
    
    final uri = Uri.parse('$_baseUrl/$productId').replace(queryParameters: {
      if (size.isNotEmpty) 'size': size,
      if (color.isNotEmpty) 'color': color,
    });
    
    final response = await http.post(
      uri,
      headers: {
        'Content-Type': 'application/json',
        if (token != null) 'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to add favorite');
    }
  }

  Future<void> removeFavorite(int favoriteId) async {
    final token = await _getToken();
    final response = await http.delete(
      Uri.parse('$_baseUrl/$favoriteId'),
      headers: {
        'Content-Type': 'application/json',
        if (token != null) 'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to remove favorite');
    }
  }

  Future<bool> checkFavorite(String productId) async {
    final token = await _getToken();
    if (token == null) return false;
    
    final response = await http.get(
      Uri.parse('$_baseUrl/$productId/check'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      return response.body == 'true';
    } else {
      return false;
    }
  }
}
