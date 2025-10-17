package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.order;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

import lombok.Data;

/**
 * Representa a entidade de pedido na API do mercado pago
 */
@Data
public class MercadoPagoOrderEntity {

	private long id;

	private MercadoPagoOrderNotificationStatus status;

	private String external_reference;

	private String preference_id;

	private List<Payment> payments;

	private List<Shipment> shipments;

	private Map<String, Object> payouts;

	private Collector collector;

	private String marketplace;

	private OffsetDateTime date_created;

	private OffsetDateTime last_updated;

	private double shipping_cost;

	private double total_amount;

	private String site_id;

	private double paid_amount;

	private double refunded_amount;

	private Payer payer;

	private List<Item> items;

	private boolean cancelled;

	private String additional_info;

	private String application_id;

	private MercadoPagoOrderStatus order_status;

	@Data
	public static class Payment {

		private long id;

		private double transaction_amount;

		private double total_paid_amount;

		private double shipping_cost;

		private String currency_id;

		private PaymentStatus status;

		private String status_detail;

		private OffsetDateTime date_approved;

		private OffsetDateTime date_created;

		private OffsetDateTime last_modified;

		private double amount_refunded;
	}

	@Data
	public static class Shipment {

		private long id;

		private String shipment_type;

		private String shipping_mode;

		private String status;

		private List<ShipmentItem> items;

		private OffsetDateTime date_created;

		private OffsetDateTime last_modified;

		private OffsetDateTime date_first_printed;

		private int service_id;

		private long sender_id;

		private long receiver_id;

		private ReceiverAddress receiver_address;

		private ShippingOption shipping_option;

		@Data

		public static class ShipmentItem {

			private String id;

			private String description;

			private int quantity;

			private String dimensions;
		}

		@Data

		public static class ReceiverAddress {

			private long id;

			private String address_line;

			private City city;

			private State state;

			private Country country;

			private double latitude;

			private double longitude;

			private String comment;

			private String contact;

			private long phone;

			private int zip_code;

			private StreetName street_name;

			private int street_number;

			@Data

			public static class City {

				private String name;
			}

			@Data

			public static class State {

				private String id;

				private String name;
			}

			@Data

			public static class Country {

				private String id;

				private String name;
			}

			@Data

			public static class StreetName {

				private String en;

				private String pt;

				private String es;
			}
		}

		@Data

		public static class ShippingOption {

			private long id;

			private double cost;

			private String currency_id;

			private long shipping_method_id;

			private EstimatedDelivery estimated_delivery;

			private String name;

			private double list_cost;

			private Speed speed;

			@Data

			public static class EstimatedDelivery {

				private OffsetDateTime date;
			}

			@Data

			public static class Speed {

				private int handling;

				private int shipping;
			}
		}
	}

	@Data
	public static class Collector {

		private long id;

		private String email;

		private String nickname;
	}

	@Data
	public static class Payer {

		private long id;
	}

	@Data
	public static class Item {

		private String id;

		private String category_id;

		private String currency_id;

		private String description;

		private String picture_url;

		private String title;

		private int quantity;

		private double unit_price;
	}
}
