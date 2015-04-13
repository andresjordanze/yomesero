json.array!(@items) do |item|
  json.extract! item, :id, :item_name, :item_description, :item_type, :item_time, :item_price
  json.url item_url(item, format: :json)
end
