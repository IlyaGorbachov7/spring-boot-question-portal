dev_launch:
	docker build -t question-portal-dev-image .
up_dev:
	docker compose -f docker-compose-dev.yml up -d
down_dev:
	docker compose -f docker-compose-dev.yml down
up_prod:
	docker compose -f docker-compose-prod.yml up -d
down_prod:
	docker compose -f docker-compose-prod.yml down