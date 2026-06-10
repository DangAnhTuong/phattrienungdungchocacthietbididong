import 'package:flutter/material.dart';

class AddToFavoritesBottomSheet extends StatefulWidget {
  final Function(String) onAdd;
  final Function(String) onSizeSelected;

  const AddToFavoritesBottomSheet({
    Key? key,
    required this.onAdd,
    required this.onSizeSelected,
  }) : super(key: key);

  @override
  _AddToFavoritesBottomSheetState createState() => _AddToFavoritesBottomSheetState();
}

class _AddToFavoritesBottomSheetState extends State<AddToFavoritesBottomSheet> {
  final List<String> sizes = ['XS', 'S', 'M', 'L', 'XL'];
  String? selectedSize;

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        color: Color(0xFFF9F9F9),
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(34),
          topRight: Radius.circular(34),
        ),
      ),
      padding: const EdgeInsets.only(top: 14, bottom: 34),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          // Drag handle
          Container(
            width: 60,
            height: 6,
            decoration: BoxDecoration(
              color: Colors.grey.shade400,
              borderRadius: BorderRadius.circular(3),
            ),
          ),
          const SizedBox(height: 16),
          const Text(
            'Select size',
            style: TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
          ),
          const SizedBox(height: 24),
          // Size buttons
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: Wrap(
              spacing: 16,
              runSpacing: 16,
              children: sizes.map((size) {
                bool isSelected = size == selectedSize;
                return GestureDetector(
                  onTap: () {
                    setState(() {
                      selectedSize = size;
                    });
                    widget.onSizeSelected(size);
                  },
                  child: Container(
                    width: (MediaQuery.of(context).size.width - 32 - 32) / 3, // 3 columns, minus padding
                    height: 40,
                    alignment: Alignment.center,
                    decoration: BoxDecoration(
                      color: isSelected ? const Color(0xFFDB3022) : Colors.white,
                      border: Border.all(
                        color: isSelected ? const Color(0xFFDB3022) : Colors.grey.shade300,
                      ),
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: Text(
                      size,
                      style: TextStyle(
                        color: isSelected ? Colors.white : Colors.black,
                        fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
                      ),
                    ),
                  ),
                );
              }).toList(),
            ),
          ),
          const SizedBox(height: 24),
          // Size info
          const Divider(height: 1),
          ListTile(
            title: const Text('Size info'),
            trailing: const Icon(Icons.chevron_right),
            onTap: () {
              // TODO: Show size info
            },
          ),
          const Divider(height: 1),
          const SizedBox(height: 24),
          // Add to favorites button
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: SizedBox(
              width: double.infinity,
              height: 48,
              child: ElevatedButton(
                onPressed: selectedSize == null ? null : () => widget.onAdd(selectedSize!),
                style: ElevatedButton.styleFrom(
                  backgroundColor: const Color(0xFFDB3022),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(25),
                  ),
                  elevation: 5,
                  shadowColor: const Color(0xFFDB3022).withOpacity(0.5),
                ),
                child: const Text(
                  'ADD TO FAVORITES',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
