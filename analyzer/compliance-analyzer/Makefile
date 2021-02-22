.PHONY: all run clean

all: clean run

clean:
	# Brings down previous execution's resources, if any
	docker-compose \
		--file src/main/resources/docker-compose.yml \
		down \
		--volumes \
		--remove-orphans

	# Pruning exited containers
	docker container prune --force

run:
	# Starts Quartermaster
	docker-compose \
		--file src/main/resources/docker-compose.yml \
		up \
		--remove-orphans
