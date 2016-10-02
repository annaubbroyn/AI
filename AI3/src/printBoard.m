folder = 'C:\Users\Anna\Documents\GitHub\AI\AI3\solutions\';
name = 'solution-test.txt';
fullname = [folder name];
data = importdata(fullname);
[rows,m] = size(data);
x = strsplit(data{1,1},{', ','[',']'});
y = strsplit(data{2,1},{', ','[',']'});
line = strsplit(data{3,1},{', ','[',']'});
cols = length(line);
water = zeros(rows-2,cols);
mountain = zeros(rows-2,cols);
forest = zeros(rows-2,cols);
grassland = zeros(rows-2,cols);
road = zeros(rows-2,cols);
obstical = zeros(rows-2,cols);
trace = zeros(rows-2,cols);
start = zeros(rows-2,cols);
final = zeros(rows-2,cols);
for i = 1:length(x)
    trace(str2num(y{i})+1,str2num(x{i})+1) = 1;
end
for i = 3:rows
    line = strsplit(data{i,1},{', ','[',']'});
    disp(line)
    for j = 2:length(line)-1
        if(str2double(line{j}) == -1)
            obstical(i-2,j-1) = 1;
        elseif(str2double(line{j}) == -2)
            start(i-2,j-1) = 1;
        elseif(str2double(line{j}) == -3)
            final(i-2,j-1) = 1;
        elseif(str2double(line{j}) == 1)
            road(i-2,j-1) = 1;
        elseif(str2double(line{j}) == 5)
            grassland(i-2,j-1) = 1;
        elseif(str2double(line{j}) == 10)
            forest(i-2,j-1) = 1;
        elseif(str2double(line{j}) == 50)
            mountain(i-2,j-1) = 1;
        elseif(str2double(line{j}) == 100)
            water(i-2,j-1) = 1;
        end
    end
end

roadColor = [0.9,0.85,0.8];
forestColor = [0,0.4,0];
grasslandColor = [0,0.7,0];
waterColor = [0,0,1];
mountainColor = [0.5,0.5,0.5];
startColor = [1,0,0];
finalColor = [0,1,0];

figure(1)
hold on
%spy(road,'sy',12)
spy(grassland,'sg',12)
spy(forest,'sy',12)
spy(mountain,'sm',12)
spy(water,'sb',12)
spy(obstical,'sk',23)
spy(trace,'.k')
spy(start,'or',12)
spy(final,'oc',12)
markerSt = findall(gca,'color','r');
markerFi = findall(gca,'color','c');
markerOb = findall(gca,'color','k');
markerRo = findall(gca,'color','y');
markerGr = findall(gca,'color','g');
markerFo = findall(gca,'color','y');
markerMo = findall(gca,'color','m');
markerWa = findall(gca,'color','b');
set(markerRo,'MarkerFaceColor',roadColor,'MarkerEdgeColor',roadColor);
set(markerSt,'MarkerFaceColor',startColor,'MarkerEdgeColor',startColor);
set(markerFi,'MarkerFaceColor',finalColor,'MarkerEdgeColor',finalColor);
set(markerOb,'MarkerFaceColor','k','MarkerEdgeColor','k');
set(markerGr,'MarkerFaceColor',grasslandColor, 'MarkerEdgeColor',grasslandColor);
set(markerFo,'MarkerFaceColor',forestColor, 'MarkerEdgeColor',forestColor);
set(markerMo,'MarkerFaceColor',mountainColor, 'MarkerEdgeColor',mountainColor);
set(markerWa,'MarkerFaceColor',waterColor,'MarkerEdgeColor',waterColor);
axis([0.5 cols-1.5 0.5 rows-1.5])
box off;
set(gca,'xcolor',get(gcf,'color'));
set(gca,'ycolor',get(gcf,'color'));
set(gca,'xtick',[]);
set(gca,'ytick',[]);
set(gca,'Color',[0.85,0.8,0.75]);




