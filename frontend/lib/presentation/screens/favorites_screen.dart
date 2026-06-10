import 'package:flutter/material.dart';
import '../../data/services/favorite_service.dart';

class FavoritesScreen extends StatefulWidget {
  const FavoritesScreen({Key? key}) : super(key: key);

  @override
  _FavoritesScreenState createState() => _FavoritesScreenState();
}

class _FavoritesScreenState extends State<FavoritesScreen> {
  bool isGridView = false;
  final FavoriteService _favoriteService = FavoriteService();
  List<dynamic> favorites = [];
  bool isLoading = true;
  String selectedCategory = 'All';

  @override
  void initState() {
    super.initState();
    _loadFavorites();
  }

  Future<void> _loadFavorites() async {
    setState(() {
      isLoading = true;
    });
    try {
      final data = await _favoriteService.getFavorites();
      setState(() {
        favorites = data;
        isLoading = false;
      });
    } catch (e) {
      print('Error loading favorites: $e');
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> _removeFavorite(int id) async {
    try {
      await _favoriteService.removeFavorite(id);
      _loadFavorites();
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to remove favorite: $e')),
      );
    }
  }

  List<String> _extractCategories() {
    Set<String> categories = {'All'};
    for (var item in favorites) {
      if (item['categories'] != null) {
        for (var cat in item['categories']) {
          categories.add(cat.toString());
        }
      }
    }
    return categories.toList();
  }

  Widget _buildCategoryTabs() {
    final categories = _extractCategories();
    return SizedBox(
      height: 40,
      child: ListView.builder(
        scrollDirection: Axis.horizontal,
        padding: const EdgeInsets.symmetric(horizontal: 16),
        itemCount: categories.length,
        itemBuilder: (context, index) {
          final cat = categories[index];
          final isSelected = cat == selectedCategory;
          return Padding(
            padding: const EdgeInsets.only(right: 12),
            child: GestureDetector(
              onTap: () {
                setState(() {
                  selectedCategory = cat;
                });
              },
              child: Container(
                padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 8),
                decoration: BoxDecoration(
                  color: isSelected ? Colors.black : Colors.black87,
                  borderRadius: BorderRadius.circular(20),
                ),
                alignment: Alignment.center,
                child: Text(
                  cat,
                  style: const TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          );
        },
      ),
    );
  }

  Widget _buildFilterBar() {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Row(
            children: [
              const Icon(Icons.filter_list, size: 24),
              const SizedBox(width: 8),
              const Text('Filters', style: TextStyle(fontSize: 14)),
            ],
          ),
          Row(
            children: [
              const Icon(Icons.swap_vert, size: 24),
              const SizedBox(width: 8),
              const Text('Price: lowest to high', style: TextStyle(fontSize: 14)),
            ],
          ),
          IconButton(
            icon: Icon(isGridView ? Icons.view_module : Icons.view_list, size: 24),
            onPressed: () {
              setState(() {
                isGridView = !isGridView;
              });
            },
          ),
        ],
      ),
    );
  }

  Widget _buildStarRating(double rating, int count) {
    int fullStars = rating.floor();
    bool hasHalfStar = (rating - fullStars) >= 0.5;

    return Row(
      children: [
        ...List.generate(5, (index) {
          if (index < fullStars) {
            return const Icon(Icons.star, color: Color(0xFFFFBA49), size: 14);
          } else if (index == fullStars && hasHalfStar) {
            return const Icon(Icons.star_half, color: Color(0xFFFFBA49), size: 14);
          } else {
            return const Icon(Icons.star_border, color: Colors.grey, size: 14);
          }
        }),
        const SizedBox(width: 4),
        Text('($count)', style: const TextStyle(color: Colors.grey, fontSize: 12)),
      ],
    );
  }

  Widget _buildListView(List<dynamic> items) {
    return ListView.builder(
      padding: const EdgeInsets.all(16),
      itemCount: items.length,
      itemBuilder: (context, index) {
        final item = items[index];
        return Padding(
          padding: const EdgeInsets.only(bottom: 28), // Space between items
          child: Stack(
            clipBehavior: Clip.none,
            children: [
              Container(
                height: 120,
                // NO margin here, so Stack bounds exactly match the white card
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(8),
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.2),
                    spreadRadius: 1,
                    blurRadius: 4,
                    offset: const Offset(0, 2),
                  ),
                ],
              ),
              child: Row(
            children: [
              // Image
              Stack(
                children: [
                  ClipRRect(
                    borderRadius: const BorderRadius.only(
                      topLeft: Radius.circular(8),
                      bottomLeft: Radius.circular(8),
                    ),
                    child: item['imageUrl'] != null
                        ? (item['imageUrl'].toString().startsWith('http')
                            ? Image.network(
                                item['imageUrl'],
                                width: 120,
                                height: 120,
                                fit: BoxFit.cover,
                              )
                            : Image.asset(
                                item['imageUrl'],
                                width: 120,
                                height: 120,
                                fit: BoxFit.cover,
                              ))
                        : Container(width: 120, height: 120, color: Colors.grey.shade200),
                  ),
                  if (item['discountTag'] != null && item['discountTag'].isNotEmpty)
                    Positioned(
                      top: 8,
                      left: 8,
                      child: Container(
                        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                        decoration: BoxDecoration(
                          color: const Color(0xFFDB3022),
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: Text(
                          item['discountTag'].toString().startsWith('-') ? item['discountTag'] : '-${item['discountTag']}',
                          style: const TextStyle(color: Colors.white, fontSize: 10, fontWeight: FontWeight.bold),
                        ),
                      ),
                    )
                  else if (item['isNewBadge'] == true)
                    Positioned(
                      top: 8,
                      left: 8,
                      child: Container(
                        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                        decoration: BoxDecoration(
                          color: Colors.black,
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: const Text(
                          'NEW',
                          style: TextStyle(color: Colors.white, fontSize: 10, fontWeight: FontWeight.bold),
                        ),
                      ),
                    ),
                ],
              ),
              // Details
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.all(12),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            item['brand'] ?? 'Brand',
                            style: const TextStyle(color: Colors.grey, fontSize: 12),
                          ),
                          GestureDetector(
                            onTap: () => _removeFavorite(item['id']),
                            child: const Icon(Icons.close, color: Colors.grey, size: 20),
                          ),
                        ],
                      ),
                      Text(
                        item['productName'] ?? 'Product Name',
                        style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16, fontFamily: 'Metropolis'),
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                      ),
                      const SizedBox(height: 4),
                      Row(
                        children: [
                          Text('Color: ${item['color'] ?? 'N/A'}', style: const TextStyle(color: Colors.grey, fontSize: 12)),
                          const SizedBox(width: 16),
                          Text('Size: ${item['size'] ?? 'N/A'}', style: const TextStyle(color: Colors.grey, fontSize: 12)),
                        ],
                      ),
                      const Spacer(),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            '${item['price']}\$',
                            style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(right: 32),
                            child: _buildStarRating(item['rating']?.toDouble() ?? 0.0, item['ratingCount'] ?? 0),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
          // Red Shopping Bag Icon for ListView
          Positioned(
            right: 0,
            bottom: -16, // Center of the bag icon on the bottom edge
            child: Container(
              padding: const EdgeInsets.all(8), // Reduce padding slightly to match size in Lists.png
              decoration: BoxDecoration(
                color: const Color(0xFFDB3022),
                shape: BoxShape.circle,
                boxShadow: [
                  BoxShadow(
                    color: const Color(0xFFDB3022).withOpacity(0.5),
                    spreadRadius: 1,
                    blurRadius: 4,
                    offset: const Offset(0, 2),
                  ),
                ],
              ),
              child: const Icon(Icons.shopping_bag, color: Colors.white, size: 20),
            ),
          ),
        ],
      ),
    );
  },
    );
  }

  Widget _buildGridView(List<dynamic> items) {
    return GridView.builder(
      padding: const EdgeInsets.all(16),
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        childAspectRatio: 0.5, // Changed to 0.5 to give enough vertical space and prevent overflow
        crossAxisSpacing: 16,
        mainAxisSpacing: 16,
      ),
      itemCount: items.length,
      itemBuilder: (context, index) {
        final item = items[index];
        return Stack(
          children: [
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Stack(
                  children: [
                    ClipRRect(
                      borderRadius: BorderRadius.circular(8),
                      child: item['imageUrl'] != null
                          ? (item['imageUrl'].toString().startsWith('http')
                              ? Image.network(
                                  item['imageUrl'],
                                  height: 180,
                                  width: double.infinity,
                                  fit: BoxFit.cover,
                                )
                              : Image.asset(
                                  item['imageUrl'],
                                  height: 180,
                                  width: double.infinity,
                                  fit: BoxFit.cover,
                                ))
                          : Container(height: 180, width: double.infinity, color: Colors.grey.shade200),
                    ),
                    if (item['discountTag'] != null && item['discountTag'].isNotEmpty)
                      Positioned(
                        top: 8,
                        left: 8,
                        child: Container(
                          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                          decoration: BoxDecoration(
                            color: const Color(0xFFDB3022),
                            borderRadius: BorderRadius.circular(12),
                          ),
                          child: Text(
                            item['discountTag'].toString().startsWith('-') ? item['discountTag'] : '-${item['discountTag']}',
                            style: const TextStyle(color: Colors.white, fontSize: 10, fontWeight: FontWeight.bold),
                          ),
                        ),
                      )
                    else if (item['isNewBadge'] == true)
                      Positioned(
                        top: 8,
                        left: 8,
                        child: Container(
                          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                          decoration: BoxDecoration(
                            color: Colors.black,
                            borderRadius: BorderRadius.circular(12),
                          ),
                          child: const Text(
                            'NEW',
                            style: TextStyle(color: Colors.white, fontSize: 10, fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    Positioned(
                      top: 8,
                      right: 8,
                      child: GestureDetector(
                        onTap: () => _removeFavorite(item['id']),
                        child: Container(
                          padding: const EdgeInsets.all(4),
                          decoration: const BoxDecoration(
                            color: Colors.white,
                            shape: BoxShape.circle,
                          ),
                          child: const Icon(Icons.close, color: Colors.grey, size: 16),
                        ),
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 8),
                _buildStarRating(item['rating']?.toDouble() ?? 0.0, item['ratingCount'] ?? 0),
                const SizedBox(height: 4),
                Text(
                  item['brand'] ?? 'Brand',
                  style: const TextStyle(color: Colors.grey, fontSize: 12),
                ),
                Text(
                  item['productName'] ?? 'Product Name',
                  style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16, fontFamily: 'Metropolis'),
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                ),
                const SizedBox(height: 4),
                Row(
                  children: [
                    Text('Color: ${item['color'] ?? 'N/A'}', style: const TextStyle(color: Colors.grey, fontSize: 12)),
                    const SizedBox(width: 8),
                    Text('Size: ${item['size'] ?? 'N/A'}', style: const TextStyle(color: Colors.grey, fontSize: 12)),
                  ],
                ),
                const SizedBox(height: 4),
                Row(
                  children: [
                    if (item['originalPrice'] != null && item['originalPrice'] != item['price'])
                      Text(
                        '${item['originalPrice']}\$',
                        style: const TextStyle(
                          color: Colors.grey,
                          decoration: TextDecoration.lineThrough,
                          fontSize: 14,
                        ),
                      ),
                    if (item['originalPrice'] != null && item['originalPrice'] != item['price'])
                      const SizedBox(width: 4),
                    Text(
                      '${item['price']}\$',
                      style: TextStyle(
                        color: (item['originalPrice'] != null && item['originalPrice'] != item['price'])
                            ? const Color(0xFFDB3022)
                            : Colors.black,
                        fontWeight: FontWeight.bold,
                        fontSize: 14,
                      ),
                    ),
                  ],
                ),
              ],
            ),
            Positioned(
              right: 0,
              top: 164, // Overlap the image bottom right
              child: Container(
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: const Color(0xFFDB3022),
                  shape: BoxShape.circle,
                  boxShadow: [
                    BoxShadow(
                      color: const Color(0xFFDB3022).withOpacity(0.5),
                      spreadRadius: 1,
                      blurRadius: 4,
                      offset: const Offset(0, 2),
                    ),
                  ],
                ),
                child: const Icon(Icons.shopping_bag, color: Colors.white, size: 20),
              ),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    List<dynamic> filteredFavorites = favorites;
    if (selectedCategory != 'All') {
      filteredFavorites = favorites.where((item) {
        if (item['categories'] != null) {
          return (item['categories'] as List).contains(selectedCategory);
        }
        return false;
      }).toList();
    }

    return Scaffold(
      backgroundColor: const Color(0xFFF9F9F9),
      appBar: AppBar(
        backgroundColor: const Color(0xFFF9F9F9),
        elevation: 0,
        centerTitle: isGridView ? true : false,
        title: isGridView
            ? const Text(
                'Favorites',
                style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
              )
            : null,
        actions: [
          IconButton(
            icon: const Icon(Icons.search, color: Colors.black),
            onPressed: () {},
          ),
        ],
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          if (!isGridView)
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 16),
              child: Text(
                'Favorites',
                style: TextStyle(fontSize: 34, fontWeight: FontWeight.bold, color: Colors.black, fontFamily: 'Metropolis'),
              ),
            ),
          const SizedBox(height: 16),
          _buildCategoryTabs(),
          _buildFilterBar(),
          Expanded(
            child: isLoading
                ? const Center(child: CircularProgressIndicator())
                : filteredFavorites.isEmpty
                    ? const Center(child: Text('No favorites found.'))
                    : isGridView
                        ? _buildGridView(filteredFavorites)
                        : _buildListView(filteredFavorites),
          ),
        ],
      ),
    );
  }
}
