import random, math

def generate_property_file(numberOfProcesses, meanCpuBurst, seed, count):
    properties = {
        'numberOfProcesses': numberOfProcesses,
        'staticPriority': 0,
        'meanInterArrival': 150.0,
        'meanCpuBurst': meanCpuBurst,
        'meanIOBurst': 15.0,
        'meanNumberBursts': 2.0,
        'seed': seed
    }
    file_name = f'input_parameters{count}.prp'
    with open(file_name, 'w') as file:
        for key, value in properties.items():
            file.write(f'{key}={value}\n')

def main():
    count = 1
    for numberOfProcesses in range(5, 21, 5):
        for meanCpuBurst in range(5, 41, 5):
            seed = random.randint(0, math.pow(10, 12))
            generate_property_file(numberOfProcesses, meanCpuBurst, seed, count)
            count += 1

if __name__ == "__main__":
    main()
