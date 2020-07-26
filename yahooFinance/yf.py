import yfinance as yf
data_df = yf.download("AAPL", start="2020-01-01", end="2020-02-02")
data_df.to_csv('aapl.csv')
data_df = yf.download("GOOGL", start="2020-01-01", end="2020-02-02")
data_df.to_csv('googl.csv')
