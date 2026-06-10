import psycopg2

try:
    conn = psycopg2.connect(
        dbname="auth_db",
        user="postgres",
        password="1",
        host="localhost",
        port="5432"
    )
    cur = conn.cursor()
    cur.execute("""
        UPDATE products p
        SET rating = COALESCE((SELECT AVG(rating) FROM product_reviews r WHERE r.product_id = p.id), 0),
            rating_count = (SELECT COUNT(*) FROM product_reviews r WHERE r.product_id = p.id);
    """)
    conn.commit()
    cur.close()
    conn.close()
    print("Successfully synchronized product ratings!")
except Exception as e:
    print("Error:", e)
