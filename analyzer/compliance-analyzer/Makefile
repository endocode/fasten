.PHONY: run

run: clean
	# Starts Quartermaster
	docker-compose \
		--file src/main/resources/docker-compose.yml \
		up \
		--remove-orphans

clean:
	# Pruning exited containers
	docker container prune --force

	# Brings down previous execution's resources, if any
	docker-compose \
		--file src/main/resources/docker-compose.yml \
		down \
		--volumes \
		--remove-orphans
